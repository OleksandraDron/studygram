package org.sashkadron.studygram.service.course.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.repository.course.SubjectCourseRepository;
import org.sashkadron.studygram.repository.course.entity.SubjectCourse;
import org.sashkadron.studygram.service.course.SubjectCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubjectCourseServiceImpl implements SubjectCourseService {

	private final SubjectCourseRepository subjectCourseRepository;

	@Override
	public SubjectCourse save(SubjectCourse subjectCourse) {
		return subjectCourseRepository.save(subjectCourse);
	}

	@Override
	public SubjectCourse findById(UUID courseId) {
		return subjectCourseRepository.findById(courseId).orElseThrow();
	}

	@Override
	@Transactional
	public void deleteById(UUID courseId) {
		subjectCourseRepository.deleteById(courseId);
	}

	@Override
	public List<SubjectCourse> findAll() {
		return subjectCourseRepository.findAll();
	}
}
