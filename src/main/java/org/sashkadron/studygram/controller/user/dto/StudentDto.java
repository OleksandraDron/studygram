package org.sashkadron.studygram.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends UserDto {

	private String startStudyYear;

	private StudentFlowDto studentFlow;

	private Instant graduatedDate;

}
