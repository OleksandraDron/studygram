package org.sashkadron.studygram.controller.analysis.facade;

import org.sashkadron.studygram.controller.analysis.dto.*;

import java.util.UUID;

public interface CourseAnalysisFacade {

	CourseProgressDto getCourseAnalysis(UUID courseId);

	CourseAvgProgressDto getCourseAvgAnalysis(UUID courseId);

	CourseGradesProgressDto getCourseGradesAnalysis(UUID courseId);

	CourseBarsProgressDto getCourseBarsAnalysis(UUID courseId);

	CourseStudentProgressDto getCourseStudentAnalysis(UUID courseId);

	StudentCourseGradesDto getCourseStudentGradesAnalysis(UUID courseId);

}
