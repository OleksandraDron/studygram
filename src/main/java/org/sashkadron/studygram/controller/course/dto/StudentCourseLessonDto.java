package org.sashkadron.studygram.controller.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.controller.user.dto.StudentDto;
import org.sashkadron.studygram.repository.course.entity.types.StudentPresence;

import java.time.Instant;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseLessonDto {

	private StudentDto student;

	private Integer mark;

	private Integer interest;

	private Integer relevance;

	private StudentPresence presentState;

	private Instant updatedPresentDate;

}
