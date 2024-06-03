package org.sashkadron.studygram.repository.course;

import org.sashkadron.studygram.repository.course.entity.LectureSubjectCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LectureSubjectCourseRepository extends JpaRepository<LectureSubjectCourse, UUID> {

	@EntityGraph(attributePaths = "subjectCourse.subject")
	Page<LectureSubjectCourse> findAllByLecturerId(UUID lecturerId, Pageable pageable);
}
