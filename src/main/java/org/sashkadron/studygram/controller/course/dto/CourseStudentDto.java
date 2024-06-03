package org.sashkadron.studygram.controller.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.controller.user.dto.StudentDto;

import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentDto {

	private UUID subjectCourseId;

	private UUID studentId;

	private StudentDto student;

	private Integer finalMark;

}
