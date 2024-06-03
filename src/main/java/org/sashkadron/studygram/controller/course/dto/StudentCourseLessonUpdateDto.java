package org.sashkadron.studygram.controller.course.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.repository.course.entity.types.StudentPresence;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseLessonUpdateDto {

	@PositiveOrZero
	private Integer mark;

	@NotNull
	private StudentPresence presentState;

	@Min(0)
	@Max(5)
	private Integer relevance;

	@Min(0)
	@Max(5)
	private Integer interest;

}
