package org.sashkadron.studygram.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentFlowDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	UUID id;

	private String title;

	private String flowCode;

}
