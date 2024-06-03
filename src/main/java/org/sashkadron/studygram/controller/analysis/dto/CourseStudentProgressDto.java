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
public class CourseStudentProgressDto {

	@Builder.Default
	List<CourseStudentProgressDto.GroupedStudentBarsDto> bars = new ArrayList<>();

	@Getter
	@SuperBuilder(toBuilder = true)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupedStudentBarsDto {

		String title;

		double avgPresence;

		double avgGrade;

	}

}
