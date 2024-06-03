package org.sashkadron.studygram.repository.course;

import org.jetbrains.annotations.NotNull;
import org.sashkadron.studygram.repository.course.entity.CourseLesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseLessonRepository extends JpaRepository<CourseLesson, UUID>, JpaSpecificationExecutor<CourseLesson> {

	void deleteBySubjectCourseIdAndId(UUID courseId, UUID lessonId);

	Page<CourseLesson> findAllBySubjectCourseId(UUID courseId, Pageable pageable);

	@Query("""
			select distinct cl from CourseLesson cl join cl.subjectCourse sc join sc.studentSubjectCourse ssc
			where ssc.studentId = ?1 and cl.startDate >= ?2 and cl.startDate < ?3""")
	List<CourseLesson> findAllByStudentIdAndStartDateInRange(UUID studentId, Instant startDate, Instant endDate, Sort sort);

	@NotNull
	@EntityGraph(attributePaths = {"subjectCourse.subject","subjectCourse.teachers.lecturer"})
	Optional<CourseLesson> findById(@NotNull UUID id);

	@NotNull
	@EntityGraph(attributePaths = {"subjectCourse.subject","subjectCourse.teachers.lecturer"})
	Page<CourseLesson> findAll(@NotNull Specification<CourseLesson> spec, @NotNull Pageable pageable);

	long countBySubjectCourseIdAndEndDateBefore(UUID courseId, Instant now);
}
