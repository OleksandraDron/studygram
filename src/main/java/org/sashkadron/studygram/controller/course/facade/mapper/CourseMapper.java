package org.sashkadron.studygram.controller.course.facade.mapper;

import org.mapstruct.*;
import org.sashkadron.studygram.controller.course.dto.*;
import org.sashkadron.studygram.controller.user.dto.LecturerDto;
import org.sashkadron.studygram.controller.user.dto.StudentFlowDto;
import org.sashkadron.studygram.repository.course.entity.*;
import org.sashkadron.studygram.repository.user.entity.Lecturer;
import org.sashkadron.studygram.repository.user.entity.StudentFlow;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	SubjectCourseDto toSubjectCourseDto(SubjectCourse subjectCourse);

	@Mapping(target = "subject", source = "subject.title")
	@Mapping(target = "description", source = "subject.description")
	@Mapping(target = "credits", source = "subject.credits")
	SubjectCourseRespDto toSubjectCourseRespDto(SubjectCourse subjectCourse);

	SubjectCourse toSubjectCourse(SubjectCourseDto subjectCourseDto);


	@Mapping(target = "id", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void patchSubjectCourse(SubjectCourseDto subjectCourseDto, @MappingTarget SubjectCourse subjectCourse);

	@Mapping(target = "subjectCourseId", source = "subjectCourseId")
    CourseLesson toCourseLesson(UUID subjectCourseId, CourseLessonDto courseLessonDto);

	CourseLessonDto toCourseLessonDto(CourseLesson courseLesson);

	@Mapping(target = "teachers", source = "subjectCourse.teachers")
	@Mapping(target = "course", source = "subjectCourse")
	CourseLessonRespDto toCourseLessonRespDto(CourseLesson courseLesson);

	LecturerDto toLectureDto(Lecturer lecturer);

	default LecturerDto toLecturerDtoFromCourse(LectureSubjectCourse lectureSubjectCourse) {
		return toLectureDto(lectureSubjectCourse.getLecturer());
	}

	CourseStudentDto toCourseStudentDto(StudentSubjectCourse studentSubjectCourse);

	StudentCourseLessonDto toStudentCourseLessonDto(StudentCourseLesson studentCourseLesson);

	StudentFlowDto toStudentFlow(StudentFlow studentFlow);
}
