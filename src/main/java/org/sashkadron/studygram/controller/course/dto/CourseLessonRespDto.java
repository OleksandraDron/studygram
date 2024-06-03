package org.sashkadron.studygram.controller.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.controller.user.dto.LecturerDto;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class CourseLessonRespDto extends CourseLessonDto {

	SubjectCourseRespDto course;

	List<LecturerDto> teachers;

}
