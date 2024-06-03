package org.sashkadron.studygram.repository.course;

import org.jetbrains.annotations.NotNull;
import org.sashkadron.studygram.repository.course.entity.SubjectCourse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectCourseRepository extends JpaRepository<SubjectCourse, UUID> {

	@NotNull
	@EntityGraph(attributePaths = "subject")
	Optional<SubjectCourse> findById(@NotNull UUID id);

}
