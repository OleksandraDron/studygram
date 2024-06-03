package org.sashkadron.studygram.controller.course.facade;

import org.sashkadron.studygram.controller.course.dto.*;
import org.sashkadron.studygram.controller.user.dto.StudentFlowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CourseFacade {

	SubjectCourseDto createSubjectCourse(SubjectCourseDto subjectCourseDto);

	SubjectCourseDto patchSubjectCourse(UUID courseId, SubjectCourseDto subjectCourseDto);

	void deleteSubjectCourse(UUID courseId);

	Page<SubjectCourseRespDto> getSubjectCourses(Pageable pageable);

	void addStudentsToCourse(UUID courseId, StudentCourseReqDto studentCourseReqDto);

	void createCourseLessons(UUID courseId, CourseLessonReqDto courseLessonReqDto);

	void deleteCourseLesson(UUID courseId, UUID lessonId);

	Page<CourseLessonDto> getCourseLessons(UUID courseId, Pageable pageable);

	Page<CourseLessonRespDto> getLessonsList(Boolean onlyToday, Pageable pageable);

	List<CourseStudentDto> getCourseStudents(UUID courseId);

	void deleteStudentFromCourse(UUID courseId, UUID studentId);

	List<StudentCourseLessonDto> getLessonStudents(UUID lessonId);

	StudentCourseLessonDto getStudentLesson(UUID lessonId);

	StudentCourseLessonDto patchStudentLesson(UUID lessonId, UUID studentId, StudentCourseLessonUpdateDto studentCourseLessonDto);

	SubjectCourseRespDto getSubjectCourse(UUID courseId);

	List<StudentFlowDto> getStudentFlows();

	CourseLessonRespDto getLesson(UUID lessonId);

}
