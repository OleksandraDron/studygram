package org.sashkadron.studygram.controller.course.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseReqDto {

	@NotEmpty
	private Set<String> studentFlowCodes;

}
