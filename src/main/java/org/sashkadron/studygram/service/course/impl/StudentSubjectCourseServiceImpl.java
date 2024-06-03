package org.sashkadron.studygram.service.course.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.repository.course.StudentSubjectCourseRepository;
import org.sashkadron.studygram.repository.course.entity.StudentSubjectCourse;
import org.sashkadron.studygram.service.course.StudentSubjectCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentSubjectCourseServiceImpl implements StudentSubjectCourseService {

	private final StudentSubjectCourseRepository studentSubjectCourseRepository;

	@Override
	public Page<StudentSubjectCourse> findAllByStudentId(UUID studentId, Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(
				pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "subjectCourse.startDate")
		);
		return studentSubjectCourseRepository.findAllByStudentId(studentId, pageRequest);
	}

	@Override
	public StudentSubjectCourse save(StudentSubjectCourse studentSubjectCourse) {
		return studentSubjectCourseRepository.save(studentSubjectCourse);
	}

	@Override
	public boolean existsByStudentIdAndSubjectCourseId(UUID studentId, UUID courseId) {
		return studentSubjectCourseRepository.existsByStudentIdAndSubjectCourseId(studentId, courseId);
	}

	@Override
	public List<StudentSubjectCourse> findAllByCourseId(UUID courseId) {
		return studentSubjectCourseRepository.findAllBySubjectCourseId(
				courseId, Sort.by("student.studentFlow.flowCode", "student.firstname", "student.lastname")
		);
	}

	@Override
	public Set<UUID> findAllStudentIdsByCourseId(UUID courseId) {
		return studentSubjectCourseRepository.findAllStudentIdsByCourseId(courseId);
	}

	@Override
	@Transactional
	public void deleteBySubjectCourseIdAndStudentId(UUID studentId, UUID courseId) {
		studentSubjectCourseRepository.deleteBySubjectCourseIdAndStudentId(courseId, studentId);
	}

	@Override
	public void updateStudentMark(UUID studentId, UUID courseId, Integer mark) {
		studentSubjectCourseRepository.updateStudentMark(studentId, courseId, mark);
	}
}
