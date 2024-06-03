package org.sashkadron.studygram.controller.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCourseRespDto extends SubjectCourseDto {

	private String subject;

	private String description;

	private Integer credits;
}
