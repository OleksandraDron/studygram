package org.sashkadron.studygram.controller.course.facade.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.sashkadron.studygram.config.exception.AppException;
import org.sashkadron.studygram.config.security.SystemUserDetails;
import org.sashkadron.studygram.config.security.service.SecurityService;
import org.sashkadron.studygram.controller.course.dto.*;
import org.sashkadron.studygram.controller.course.facade.CourseFacade;
import org.sashkadron.studygram.controller.course.facade.mapper.CourseMapper;
import org.sashkadron.studygram.controller.user.dto.StudentFlowDto;
import org.sashkadron.studygram.repository.course.entity.*;
import org.sashkadron.studygram.repository.user.entity.Lecturer;
import org.sashkadron.studygram.service.course.*;
import org.sashkadron.studygram.service.course.filter.LessonFilter;
import org.sashkadron.studygram.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@AllArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

	private final LecturerSubjectCourseService lecturerSubjectCourseService;
	private final StudentSubjectCourseService studentSubjectCourseService;
	private final StudentCourseLessonService studentCourseLessonService;
	private final SubjectCourseService subjectCourseService;
	private final CourseLessonService courseLessonService;
	private final StudentFlowService studentFlowService;
	private final SecurityService securityService;
	private final UserService userService;

	private final CourseMapper courseMapper;

	@Override
	@Transactional
	public SubjectCourseDto createSubjectCourse(SubjectCourseDto subjectCourseDto) {
		boolean isInvalidDateRange = subjectCourseDto.getStartDate().compareTo(subjectCourseDto.getEndDate()) >= 0;
		if (isInvalidDateRange) {
			throw new AppException("Start date must be before end date");
		}

		SubjectCourse subjectCourse = subjectCourseService.save(courseMapper.toSubjectCourse(subjectCourseDto));
		Lecturer lecturer = (Lecturer) securityService.getUser();
		lecturerSubjectCourseService.save(LectureSubjectCourse.builder()
				.subjectCourseId(subjectCourse.getId())
				.lecturerId(lecturer.getId())
				.position(lecturer.getPosition())
				.build());

		return courseMapper.toSubjectCourseDto(subjectCourse);
	}

	@Override
	@Transactional
	public SubjectCourseDto patchSubjectCourse(UUID courseId, SubjectCourseDto subjectCourseDto) {
		SubjectCourse subjectCourse = subjectCourseService.findById(courseId);
		courseMapper.patchSubjectCourse(subjectCourseDto, subjectCourse);
		return courseMapper.toSubjectCourseDto(subjectCourse);
	}

	@Override
	public void deleteSubjectCourse(UUID courseId) {
		subjectCourseService.deleteById(courseId);
	}

	@Override
	public Page<SubjectCourseRespDto> getSubjectCourses(Pageable pageable) {
		SystemUserDetails userDetails = securityService.getUserDetails();

		if (userDetails.isStudent()) {
			return studentSubjectCourseService.findAllByStudentId(userDetails.getId(), pageable)
					.map(StudentSubjectCourse::getSubjectCourse)
					.map(courseMapper::toSubjectCourseRespDto);
		}

		return lecturerSubjectCourseService.findAllByLecturerId(userDetails.getId(), pageable)
				.map(LectureSubjectCourse::getSubjectCourse)
				.map(courseMapper::toSubjectCourseRespDto);

	}

	@Override
	@Transactional
	public void addStudentsToCourse(UUID courseId, StudentCourseReqDto studentCourseReqDto) {
		Page<CourseLesson> courseLessons = courseLessonService.findAllByCourseId(courseId, Pageable.unpaged());
		Set<UUID> courseStudentIds = studentSubjectCourseService.findAllStudentIdsByCourseId(courseId);

		userService.findAllStudentsByFlowCodes(studentCourseReqDto.getStudentFlowCodes())
				.stream()
				.filter(student -> !courseStudentIds.contains(student.getId()))
				.map(student -> StudentSubjectCourse.builder()
						.studentId(student.getId())
						.subjectCourseId(courseId)
						.build()
				).map(studentSubjectCourseService::save)
				//save lessons for all new course students
				.forEach(studentCourse -> courseLessons.forEach(courseLesson -> studentCourseLessonService.saveIfNotExists(
								StudentCourseLesson.builder()
										.studentId(studentCourse.getStudentId())
										.courseLessonId(courseLesson.getId())
										.build()
						))
				);
	}

	@Override
	@Transactional
	public void createCourseLessons(UUID courseId, CourseLessonReqDto courseLessonReqDto) {
		SubjectCourse subjectCourse = subjectCourseService.findById(courseId);
		Instant lessonStartDate = courseLessonReqDto.getStartDate();

		if (lessonStartDate.isAfter(subjectCourse.getEndDate()) || lessonStartDate.isBefore(subjectCourse.getStartDate())) {
			throw new AppException("Lesson start date is outside of course start/end period");
		}

		List<CourseLesson> courseLessons = new ArrayList<>();
		Integer lessonDuration = courseLessonReqDto.getDuration();
		Integer repeatEveryNDays = courseLessonReqDto.getRepeatEveryNDays();

		do {
			CourseLessonDto courseLessonDto = courseLessonReqDto.toBuilder()
					.endDate(lessonStartDate.plus(lessonDuration, ChronoUnit.MINUTES))
					.startDate(lessonStartDate)
					.build();

			courseLessons.add(courseMapper.toCourseLesson(courseId, courseLessonDto));
			lessonStartDate = lessonStartDate.plus(repeatEveryNDays, ChronoUnit.DAYS);

		} while (lessonStartDate.compareTo(subjectCourse.getEndDate()) <= 0 && repeatEveryNDays > 0);

		List<CourseLesson> savedCourseLessons = courseLessonService.saveAll(courseLessons);

		saveNewStudentLessons(subjectCourse.getId(), savedCourseLessons);
	}

	@Override
	public void deleteCourseLesson(UUID courseId, UUID lessonId) {
		courseLessonService.deleteByCourseIdAndLessonId(courseId, lessonId);
	}

	@Override
	public Page<CourseLessonDto> getCourseLessons(UUID courseId, Pageable pageable) {
		return courseLessonService
				.findAllByCourseId(courseId, pageable)
				.map(courseMapper::toCourseLessonDto);
	}

	@Override
	public Page<CourseLessonRespDto> getLessonsList(Boolean onlyToday, Pageable pageable) {
		SystemUserDetails userDetails = securityService.getUserDetails();
		Instant startDate = LocalDate.now().atStartOfDay(ZoneId.of("Europe/Kiev")).toInstant();
		Instant endDate = startDate.plus(1, ChronoUnit.DAYS);

		return courseLessonService.findAllByFilter(LessonFilter.builder()
				.lecturerId(userDetails.isLector() ? userDetails.getId() : null)
				.studentId(userDetails.isStudent() ? userDetails.getId() : null)
				.startDate(onlyToday ? startDate : null)
				.entDate(onlyToday ? endDate : null)
				.build(), pageable
		).map(courseMapper::toCourseLessonRespDto);
	}

	@Override
	public List<CourseStudentDto> getCourseStudents(UUID courseId) {
		return studentSubjectCourseService
				.findAllByCourseId(courseId)
				.stream()
				.map(courseMapper::toCourseStudentDto)
				.toList();
	}

	@Override
	public void deleteStudentFromCourse(UUID courseId, UUID studentId) {
		studentSubjectCourseService.deleteBySubjectCourseIdAndStudentId(studentId, courseId);
	}

	@Override
	public List<StudentCourseLessonDto> getLessonStudents(UUID lessonId) {
		List<StudentCourseLesson> lessonStudents = studentCourseLessonService.getLessonStudents(lessonId);
		return lessonStudents
				.stream()
				.map(courseMapper::toStudentCourseLessonDto)
				.toList();
	}

	@Override
	public StudentCourseLessonDto getStudentLesson(UUID lessonId) {
		StudentCourseLesson studentLesson = studentCourseLessonService.getStudentLesson(
				securityService.getUserId(), lessonId
		);
		return courseMapper.toStudentCourseLessonDto(studentLesson);
	}

	@Override
	@Transactional
	public StudentCourseLessonDto patchStudentLesson(UUID lessonId, UUID studentId,
													 StudentCourseLessonUpdateDto studentCourseLessonDto) {
		SystemUserDetails userDetails = securityService.getUserDetails();

		if (userDetails.isStudent() && ObjectUtils.notEqual(studentId, userDetails.getId())) {
			throw new AppException("You are not allowed to update other students lessons");
		}

		StudentCourseLesson studentLesson = studentCourseLessonService.getStudentLesson(
				studentId, lessonId
		);

		boolean isLessonNonUpdatable = Instant.now().isAfter(studentLesson.getCourseLesson().getEndDate()) ||
									   Instant.now().isBefore(studentLesson.getCourseLesson().getStartDate());
		if (userDetails.isStudent() && isLessonNonUpdatable) {
			throw new AppException("You can't update presence after/before the lesson");
		}

		if (userDetails.isLector() && Objects.nonNull(studentCourseLessonDto.getMark())) {
			Integer mark = studentCourseLessonDto.getMark();
			int incrementalMark = mark - Optional.ofNullable(studentLesson.getMark()).orElse(0);
			studentLesson.setMark(mark);
			studentSubjectCourseService.updateStudentMark(
					studentId, studentLesson.getCourseLesson().getSubjectCourseId(), incrementalMark
			);
		}

		if (userDetails.isStudent()) {
			studentLesson.setRelevance(
					Optional.ofNullable(studentCourseLessonDto.getRelevance())
							.orElse(studentLesson.getRelevance())
			);
			studentLesson.setInterest(
					Optional.ofNullable(studentCourseLessonDto.getInterest())
							.orElse(studentLesson.getInterest())
			);
		}

		studentLesson.setPresentState(studentCourseLessonDto.getPresentState());
		studentLesson.setUpdatedPresentDate(Instant.now());

		return courseMapper.toStudentCourseLessonDto(studentLesson);
	}

	@Override
	public SubjectCourseRespDto getSubjectCourse(UUID courseId) {
		SubjectCourse subjectCourse = subjectCourseService.findById(courseId);
		return courseMapper.toSubjectCourseRespDto(subjectCourse);
	}

	@Override
	public List<StudentFlowDto> getStudentFlows() {
		return studentFlowService.findAll().stream().map(courseMapper::toStudentFlow).toList();
	}

	@Override
	public CourseLessonRespDto getLesson(UUID lessonId) {
		CourseLesson lesson = courseLessonService.findById(lessonId);
		return courseMapper.toCourseLessonRespDto(lesson);
	}

	private void saveNewStudentLessons(UUID subjectCourseId, List<CourseLesson> courseLessons) {
		studentSubjectCourseService
				.findAllStudentIdsByCourseId(subjectCourseId)
				.forEach(studentId ->
						courseLessons.forEach(courseLesson -> studentCourseLessonService.save(
								StudentCourseLesson.builder()
										.courseLessonId(courseLesson.getId())
										.studentId(studentId)
										.build()
						))
				);
	}
}
