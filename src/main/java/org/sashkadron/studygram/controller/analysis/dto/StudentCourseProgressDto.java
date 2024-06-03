package org.sashkadron.studygram.controller.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseProgressDto {

	UUID studentId;

	Double grade;

	Double presence;

}
