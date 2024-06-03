package org.sashkadron.studygram.repository.course;

import org.sashkadron.studygram.repository.course.entity.StudentSubjectCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StudentSubjectCourseRepository extends JpaRepository<StudentSubjectCourse, UUID> {

	@EntityGraph(attributePaths = "subjectCourse.subject")
	Page<StudentSubjectCourse> findAllByStudentId(UUID studentId, PageRequest pageRequest);

	boolean existsByStudentIdAndSubjectCourseId(UUID studentId, UUID courseId);

	@EntityGraph(attributePaths = "student.studentFlow")
	List<StudentSubjectCourse> findAllBySubjectCourseId(UUID courseId, Sort sort);

	void deleteBySubjectCourseIdAndStudentId(UUID courseId, UUID studentId);

	@Query("select ssc.studentId from StudentSubjectCourse ssc where ssc.subjectCourseId = ?1")
	Set<UUID> findAllStudentIdsByCourseId(UUID courseId);

	@Modifying
	@Query("update StudentSubjectCourse set finalMark = (finalMark + ?3) where studentId = ?1 and subjectCourseId = ?2")
	void updateStudentMark(UUID studentId, UUID courseId, Integer mark);
}
