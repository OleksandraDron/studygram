package org.sashkadron.studygram.controller.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseLessonDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID id;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID subjectCourseId;

	@NotBlank
	private String title;

	private String description;

	@NotNull
	private Instant startDate;

	@NotNull
	@Positive
	private Integer duration;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Instant endDate;

}
