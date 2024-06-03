package org.sashkadron.studygram.service.course.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.repository.course.CourseLessonRepository;
import org.sashkadron.studygram.repository.course.entity.CourseLesson;
import org.sashkadron.studygram.service.course.CourseLessonService;
import org.sashkadron.studygram.service.course.filter.LessonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseLessonServiceImpl implements CourseLessonService {

	private final CourseLessonRepository courseLessonRepository;

	@Override
	public List<CourseLesson> saveAll(Collection<CourseLesson> courseLessons) {
		return courseLessonRepository.saveAll(courseLessons);
	}

	@Override
	@Transactional
	public void deleteByCourseIdAndLessonId(UUID courseId, UUID lessonId) {
		courseLessonRepository.deleteBySubjectCourseIdAndId(courseId, lessonId);
	}

	@Override
	public Page<CourseLesson> findAllByCourseId(UUID courseId, Pageable pageable) {
		return courseLessonRepository.findAllBySubjectCourseId(courseId, pageable);
	}

	@Override
	public List<CourseLesson> findAllByStudentIdAndStartDateInRange(UUID studentId, Instant startDate, Instant endDate) {
		return courseLessonRepository.findAllByStudentIdAndStartDateInRange(
				studentId, startDate, endDate, Sort.by(Sort.Direction.ASC, "startDate")
		);
	}

	@Override
	public CourseLesson findById(UUID lessonId) {
		return courseLessonRepository.findById(lessonId).orElseThrow();
	}

	@Override
	public Page<CourseLesson> findAllByFilter(LessonFilter lessonFilter, Pageable pageable) {
		Specification<CourseLesson> spec = Specification.where((Specification<CourseLesson>)
						(r, rq, cb) -> Optional.ofNullable(lessonFilter.getLecturerId())
								.map(lecturerId -> cb.equal(
										r.join("subjectCourse")
												.join("teachers")
												.get("lecturerId"), lecturerId
								)).orElse(null)
				).and((r, rq, cb) -> Optional.ofNullable(lessonFilter.getStudentId())
						.map(studentId -> cb.equal(r.join("studentLessons").get("studentId"), studentId)
						).orElse(null)
				).and((r, rq, cb) -> Optional.ofNullable(lessonFilter.getStartDate())
						.map(startDate -> cb.greaterThanOrEqualTo(r.get("startDate"), startDate)
						).orElse(null))
				.and((r, rq, cb) -> Optional.ofNullable(lessonFilter.getEntDate())
						.map(endDate -> cb.lessThanOrEqualTo(r.get("endDate"), endDate)
						).orElse(null)
				);
		return courseLessonRepository.findAll(spec, pageable);
	}

	@Override
	public long countFinishedByCourseId(UUID courseId) {
		return courseLessonRepository.countBySubjectCourseIdAndEndDateBefore(courseId, Instant.now());
	}
}
