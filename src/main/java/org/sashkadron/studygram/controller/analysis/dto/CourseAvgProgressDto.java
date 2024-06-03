package org.sashkadron.studygram.controller.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseAvgProgressDto {

	@Builder.Default
	Double avgPresence = 0d;

	@Builder.Default
	Double avgGrade = 0d;

	@Builder.Default
	Double avgInterestAndRelevance = 0d;

}
