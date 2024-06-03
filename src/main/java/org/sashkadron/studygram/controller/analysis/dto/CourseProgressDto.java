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
public class CourseProgressDto {

	@Builder.Default
	List<StudentCourseProgressDto> progress = new ArrayList<>();

	@Builder.Default
	Double correlation = 0d;

	@Builder.Default
	List<Integer> linearRegressionX = new ArrayList<>();

	@Builder.Default
	List<Double> linearRegressionY  = new ArrayList<>();

}
