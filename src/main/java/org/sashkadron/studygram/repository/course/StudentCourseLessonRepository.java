package org.sashkadron.studygram.repository.course;

import org.sashkadron.studygram.repository.course.entity.StudentCourseLesson;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentCourseLessonRepository extends JpaRepository<StudentCourseLesson, UUID> {

	@EntityGraph(attributePaths = "student.studentFlow")
	List<StudentCourseLesson> findAllByCourseLessonId(UUID lessonId, Sort sort);

	Optional<StudentCourseLesson> findByStudentIdAndCourseLessonId(UUID studentId, UUID lessonId);

	boolean existsByStudentIdAndCourseLessonId(UUID studentId, UUID courseLessonId);

	List<StudentCourseLesson> findAllByCourseLesson_SubjectCourseIdAndCourseLesson_EndDateBefore(UUID courseId, Instant date);

	List<StudentCourseLesson> findAllByCourseLesson_SubjectCourseIdAndStudentIdAndCourseLesson_EndDateBefore(
			UUID courseId, UUID studentId, Instant date
	);
}
