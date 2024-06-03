package org.sashkadron.studygram.service.course.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.repository.course.StudentCourseLessonRepository;
import org.sashkadron.studygram.repository.course.entity.StudentCourseLesson;
import org.sashkadron.studygram.service.course.StudentCourseLessonService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentCourseLessonServiceImpl implements StudentCourseLessonService {

	private final StudentCourseLessonRepository studentCourseLessonRepository;

	@Override
	public List<StudentCourseLesson> getLessonStudents(UUID lessonId) {
		return studentCourseLessonRepository.findAllByCourseLessonId(
				lessonId, Sort.by("student.studentFlow.flowCode", "student.firstname", "student.lastname")
		);
	}

	@Override
	@Transactional
	public StudentCourseLesson getStudentLesson(UUID studentId, UUID lessonId) {
		return studentCourseLessonRepository
				.findByStudentIdAndCourseLessonId(studentId, lessonId)
				.orElseThrow();
	}

	@Override
	public void save(StudentCourseLesson studentCourseLesson) {
		studentCourseLessonRepository.save(studentCourseLesson);
	}

	@Override
	public void saveIfNotExists(StudentCourseLesson studentCourseLesson) {
		boolean studentAlreadyHasThisLesson = studentCourseLessonRepository.existsByStudentIdAndCourseLessonId(
				studentCourseLesson.getStudentId(), studentCourseLesson.getCourseLessonId()
		);

		if (studentAlreadyHasThisLesson) {
			return;
		}

		studentCourseLessonRepository.save(studentCourseLesson);
	}

	@Override
	public List<StudentCourseLesson> findAllFinishedByCourseId(UUID courseId) {
		return studentCourseLessonRepository.findAllByCourseLesson_SubjectCourseIdAndCourseLesson_EndDateBefore(courseId, Instant.now());
	}

	@Override
	public List<StudentCourseLesson> findAllFinishedByCourseIdAndStudentId(UUID courseId, UUID studentId) {
		return studentCourseLessonRepository.findAllByCourseLesson_SubjectCourseIdAndStudentIdAndCourseLesson_EndDateBefore(
				courseId, studentId, Instant.now()
		);
	}
}
