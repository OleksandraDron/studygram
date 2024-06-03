package org.sashkadron.studygram.controller.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseGradesDto {

	@Builder.Default
	List<StudentCourseGradeDto> grades = new ArrayList<>();

	@Getter
	@SuperBuilder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StudentCourseGradeDto {

		LocalDate date;

		double grade;

	}

}
