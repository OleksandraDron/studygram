package org.sashkadron.studygram.service.course;

import org.sashkadron.studygram.repository.course.entity.StudentSubjectCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StudentSubjectCourseService {

	Page<StudentSubjectCourse> findAllByStudentId(UUID studentId, Pageable pageable);

	StudentSubjectCourse save(StudentSubjectCourse studentSubjectCourse);

	boolean existsByStudentIdAndSubjectCourseId(UUID studentId, UUID courseId);

	List<StudentSubjectCourse> findAllByCourseId(UUID courseId);

	Set<UUID> findAllStudentIdsByCourseId(UUID courseId);

	void deleteBySubjectCourseIdAndStudentId(UUID studentId, UUID courseId);

	void updateStudentMark(UUID studentId, UUID courseId, Integer mark);
}
