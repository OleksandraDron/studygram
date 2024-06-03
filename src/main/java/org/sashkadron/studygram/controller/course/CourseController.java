package org.sashkadron.studygram.controller.course;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sashkadron.studygram.controller.course.dto.*;
import org.sashkadron.studygram.controller.course.facade.CourseFacade;
import org.sashkadron.studygram.controller.user.dto.StudentFlowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CourseController {

	private final CourseFacade courseFacade;

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@PostMapping("/private/subjects/courses")
	public ResponseEntity<SubjectCourseDto> createSubjectCourse(@RequestBody @Valid SubjectCourseDto subjectCourseDto) {
		SubjectCourseDto response = courseFacade.createSubjectCourse(subjectCourseDto);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@PatchMapping("/private/subjects/courses/{courseId}")
	public ResponseEntity<SubjectCourseDto> patchSubjectCourse(@PathVariable UUID courseId,
															   @RequestBody @Valid SubjectCourseDto subjectCourseDto) {
		SubjectCourseDto response = courseFacade.patchSubjectCourse(courseId, subjectCourseDto);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@DeleteMapping("/private/subjects/courses/{courseId}")
	public ResponseEntity<Void> deleteSubjectCourse(@PathVariable UUID courseId) {
		courseFacade.deleteSubjectCourse(courseId);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@PostMapping("/private/subjects/courses/{courseId}/lessons")
	public ResponseEntity<Void> createCourseLessons(@PathVariable UUID courseId,
													@RequestBody @Valid CourseLessonReqDto courseLessonReqDto) {
		courseFacade.createCourseLessons(courseId, courseLessonReqDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/private/subjects/courses/{courseId}/lessons")
	public ResponseEntity<Page<CourseLessonDto>> getCourseLessons(@PathVariable UUID courseId,
																  @SortDefault(value = "startDate")
																  Pageable pageable) {
		Page<CourseLessonDto> response = courseFacade.getCourseLessons(courseId, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private/subjects/courses/lessons")
	public ResponseEntity<Page<CourseLessonRespDto>> getLessonsList(@RequestParam(defaultValue = "false")
																	Boolean onlyToday,
																	@SortDefault(value = "startDate")
																	Pageable pageable) {
		Page<CourseLessonRespDto> response = courseFacade.getLessonsList(onlyToday, pageable);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@DeleteMapping("/private/subjects/courses/{courseId}/lessons/{lessonId}")
	public ResponseEntity<Void> deleteCourseLesson(@PathVariable UUID courseId, @PathVariable UUID lessonId) {
		courseFacade.deleteCourseLesson(courseId, lessonId);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/private/subjects/courses/lessons/{lessonId}/students")
	public ResponseEntity<List<StudentCourseLessonDto>> getLessonStudents(@PathVariable UUID lessonId) {
		List<StudentCourseLessonDto> response = courseFacade.getLessonStudents(lessonId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private/subjects/courses/lessons/{lessonId}")
	public ResponseEntity<CourseLessonRespDto> getLesson(@PathVariable UUID lessonId) {
		CourseLessonRespDto response = courseFacade.getLesson(lessonId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/private/subjects/courses/lessons/{lessonId}/students/me")
	public ResponseEntity<StudentCourseLessonDto> getStudentLesson(@PathVariable UUID lessonId) {
		StudentCourseLessonDto response = courseFacade.getStudentLesson(lessonId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/private/subjects/courses/lessons/{lessonId}/students/{studentId}")
	public ResponseEntity<StudentCourseLessonDto> patchStudentLesson(@PathVariable UUID lessonId, @PathVariable UUID studentId,
																	 @RequestBody @Valid
																	 StudentCourseLessonUpdateDto studentCourseLessonDto) {
		StudentCourseLessonDto response = courseFacade.patchStudentLesson(lessonId, studentId, studentCourseLessonDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private/subjects/courses")
	public ResponseEntity<Page<SubjectCourseRespDto>> getSubjectCourses(@SortDefault(value = "subjectCourse.startDate", direction = DESC)
																		Pageable pageable) {
		Page<SubjectCourseRespDto> response = courseFacade.getSubjectCourses(pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private/subjects/courses/{courseId}")
	public ResponseEntity<SubjectCourseRespDto> getSubjectCourse(@PathVariable UUID courseId) {
		SubjectCourseRespDto response = courseFacade.getSubjectCourse(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@PatchMapping("/private/subjects/courses/{courseId}/students")
	public ResponseEntity<Void> addStudentsToCourse(@PathVariable UUID courseId,
													@RequestBody @Valid StudentCourseReqDto studentCourseReqDto) {
		courseFacade.addStudentsToCourse(courseId, studentCourseReqDto);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/private/subjects/courses/{courseId}/students")
	public ResponseEntity<List<CourseStudentDto>> getCourseStudents(@PathVariable UUID courseId) {
		List<CourseStudentDto> response = courseFacade.getCourseStudents(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/private/students/flows")
	public ResponseEntity<List<StudentFlowDto>> getStudentFlows() {
		List<StudentFlowDto> response = courseFacade.getStudentFlows();
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@DeleteMapping("/private/subjects/courses/{courseId}/students/{studentId}")
	public ResponseEntity<Void> deleteStudentFromCourse(@PathVariable UUID courseId,
														@PathVariable UUID studentId) {
		courseFacade.deleteStudentFromCourse(courseId, studentId);
		return ResponseEntity.noContent().build();
	}

}
