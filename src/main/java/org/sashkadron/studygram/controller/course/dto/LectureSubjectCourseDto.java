package org.sashkadron.studygram.controller.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sashkadron.studygram.controller.user.dto.LecturerDto;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureSubjectCourseDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID id;

	private UUID subjectCourseId;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private SubjectCourseDto subjectCourse;

	private UUID lecturerId;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LecturerDto lecturer;

	private String position;

}
