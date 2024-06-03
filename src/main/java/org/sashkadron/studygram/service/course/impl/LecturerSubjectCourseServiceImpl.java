package org.sashkadron.studygram.service.course.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.repository.course.LectureSubjectCourseRepository;
import org.sashkadron.studygram.repository.course.entity.LectureSubjectCourse;
import org.sashkadron.studygram.service.course.LecturerSubjectCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LecturerSubjectCourseServiceImpl implements LecturerSubjectCourseService {

	private final LectureSubjectCourseRepository lectureSubjectCourseRepository;

	@Override
	public LectureSubjectCourse save(LectureSubjectCourse lectureSubjectCourse) {
		return lectureSubjectCourseRepository.save(lectureSubjectCourse);
	}

	@Override
	public Page<LectureSubjectCourse> findAllByLecturerId(UUID lecturerId, Pageable pageable) {
		return lectureSubjectCourseRepository.findAllByLecturerId(lecturerId, pageable);
	}
}
