package org.sashkadron.studygram.controller.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseLessonReqDto extends CourseLessonDto {

	@NotNull
	@PositiveOrZero
	private Integer repeatEveryNDays;

}
