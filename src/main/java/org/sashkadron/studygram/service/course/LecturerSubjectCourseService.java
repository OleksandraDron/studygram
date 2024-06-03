package org.sashkadron.studygram.service.course;

import org.sashkadron.studygram.repository.course.entity.LectureSubjectCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LecturerSubjectCourseService {

	LectureSubjectCourse save(LectureSubjectCourse lectureSubjectCourse);

	Page<LectureSubjectCourse> findAllByLecturerId(UUID lecturerId, Pageable pageable);
}
