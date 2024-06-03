package org.sashkadron.studygram.service.course;

import org.sashkadron.studygram.repository.course.entity.StudentCourseLesson;

import java.util.List;
import java.util.UUID;

public interface StudentCourseLessonService {

	List<StudentCourseLesson> getLessonStudents(UUID lessonId);

	StudentCourseLesson getStudentLesson(UUID studentId, UUID lessonId);

	void save(StudentCourseLesson studentCourseLesson);

	void saveIfNotExists(StudentCourseLesson studentCourseLesson);

	List<StudentCourseLesson> findAllFinishedByCourseId(UUID courseId);

	List<StudentCourseLesson> findAllFinishedByCourseIdAndStudentId(UUID courseId, UUID studentId);

}
