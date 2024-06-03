package org.sashkadron.studygram.controller.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

	private UUID id;

	private String title;

	private String description;

	private Integer credits;

}
