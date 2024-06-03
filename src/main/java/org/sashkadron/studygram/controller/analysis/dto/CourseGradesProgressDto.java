package org.sashkadron.studygram.controller.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseGradesProgressDto {

	@Builder.Default
    List<GroupedGradesDto> grades = new ArrayList<>();

	@Getter
	@SuperBuilder(toBuilder = true)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupedGradesDto {

		long numberOfStudents;

		int grade;

	}

}
