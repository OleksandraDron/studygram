package org.sashkadron.studygram.controller.analysis;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.controller.analysis.dto.*;
import org.sashkadron.studygram.controller.analysis.facade.CourseAnalysisFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/private/courses")
@AllArgsConstructor
public class CourseAnalysisController {

	CourseAnalysisFacade courseAnalysisFacade;

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/{courseId}/progress/analysis")
	public ResponseEntity<CourseProgressDto> getCourseAnalysis(@PathVariable UUID courseId) {
		CourseProgressDto response = courseAnalysisFacade.getCourseAnalysis(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/{courseId}/progress/avg/analysis")
	public ResponseEntity<CourseAvgProgressDto> getCourseAvgAnalysis(@PathVariable UUID courseId) {
		CourseAvgProgressDto response = courseAnalysisFacade.getCourseAvgAnalysis(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/{courseId}/progress/grades/analysis")
	public ResponseEntity<CourseGradesProgressDto> getCourseGradesAnalysis(@PathVariable UUID courseId) {
		CourseGradesProgressDto response = courseAnalysisFacade.getCourseGradesAnalysis(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_LECTOR')")
	@GetMapping("/{courseId}/progress/bars/analysis")
	public ResponseEntity<CourseBarsProgressDto> getCourseBarsAnalysis(@PathVariable UUID courseId) {
		CourseBarsProgressDto response = courseAnalysisFacade.getCourseBarsAnalysis(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/{courseId}/students/progress/analysis")
	public ResponseEntity<CourseStudentProgressDto> getCourseStudentAnalysis(@PathVariable UUID courseId) {
		CourseStudentProgressDto response = courseAnalysisFacade.getCourseStudentAnalysis(courseId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/{courseId}/students/progress/grades/analysis")
	public ResponseEntity<StudentCourseGradesDto> getCourseStudentGradesAnalysis(@PathVariable UUID courseId) {
		StudentCourseGradesDto response = courseAnalysisFacade.getCourseStudentGradesAnalysis(courseId);
		return ResponseEntity.ok(response);
	}

}
