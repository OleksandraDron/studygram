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
public class CourseBarsProgressDto {

	@Builder.Default
    List<GroupedBarsDto> bars = new ArrayList<>();

	@Getter
	@SuperBuilder(toBuilder = true)
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupedBarsDto {

		String month;

		double avgPresence;

		double avgInterest;

		double avgRelevance;

	}

}
