package org.sashkadron.studygram.service.course;

import org.sashkadron.studygram.repository.course.entity.SubjectCourse;

import java.util.List;
import java.util.UUID;

public interface SubjectCourseService {

	SubjectCourse save(SubjectCourse subjectCourse);

	SubjectCourse findById(UUID courseId);

	void deleteById(UUID courseId);

	List<SubjectCourse> findAll();
}
