package org.sashkadron.studygram.controller.analysis.facade.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.sashkadron.studygram.config.security.service.SecurityService;
import org.sashkadron.studygram.controller.analysis.dto.*;
import org.sashkadron.studygram.controller.analysis.facade.CourseAnalysisFacade;
import org.sashkadron.studygram.repository.course.entity.StudentCourseLesson;
import org.sashkadron.studygram.repository.course.entity.StudentSubjectCourse;
import org.sashkadron.studygram.repository.course.entity.types.StudentPresence;
import org.sashkadron.studygram.service.course.CourseLessonService;
import org.sashkadron.studygram.service.course.StudentCourseLessonService;
import org.sashkadron.studygram.service.course.StudentSubjectCourseService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class CourseAnalysisFacadeImpl implements CourseAnalysisFacade {

	StudentSubjectCourseService studentSubjectCourseService;
	StudentCourseLessonService studentCourseLessonService;
	CourseLessonService courseLessonService;
	SecurityService securityService;

	@Override
	public CourseProgressDto getCourseAnalysis(UUID courseId) {
		List<StudentCourseLesson> studentLessons = studentCourseLessonService.findAllFinishedByCourseId(courseId);

		if (CollectionUtils.isEmpty(studentLessons)) {
			return CourseProgressDto.builder().build();
		}

		List<StudentCourseProgressDto> progress = studentLessons
				.stream()
				.collect(Collectors.groupingBy(StudentCourseLesson::getStudentId))
				.entrySet()
				.stream()
				.map(studentIdToLessons -> {
					UUID studentId = studentIdToLessons.getKey();
					List<StudentCourseLesson> lessons = studentIdToLessons.getValue();

					int studentGrade = lessons.stream().mapToInt(StudentCourseLesson::getMark).sum();
					double presentPercent = (double) lessons.stream()
							.map(StudentCourseLesson::getPresentState)
							.filter(StudentPresence.PRESENT::equals)
							.count() / lessons.size() * 100;

					return StudentCourseProgressDto.builder()
							.grade((double) Math.min(100, studentGrade))
							.studentId(studentId)
							.presence(presentPercent)
							.build();
				})
				.collect(Collectors.toList());

		int maxX = (int) progress.stream().mapToDouble(StudentCourseProgressDto::getPresence).max().orElse(100d);
		int minX = (int) progress.stream().mapToDouble(StudentCourseProgressDto::getPresence).min().orElse(0d);

		double[] slopeAndIntercept = linearRegression(progress);
		int[] linearRegressionX = IntStream.rangeClosed(minX, maxX).toArray();
		double[] linearRegressionY = IntStream.rangeClosed(minX, maxX)
				.mapToDouble(x -> slopeAndIntercept[0] * x + slopeAndIntercept[1])
				.toArray();

		return CourseProgressDto.builder()
				.correlation(correlationCoefficient(progress))
				.linearRegressionX(Arrays.stream(linearRegressionX).boxed().toList())
				.linearRegressionY(Arrays.stream(linearRegressionY).boxed().toList())
				.progress(progress)
				.build();
	}

	@Override
	public CourseAvgProgressDto getCourseAvgAnalysis(UUID courseId) {
		List<StudentSubjectCourse> courseStudents = studentSubjectCourseService.findAllByCourseId(courseId);
		List<StudentCourseLesson> studentLessons = studentCourseLessonService.findAllFinishedByCourseId(courseId);

		if (CollectionUtils.isEmpty(studentLessons) || CollectionUtils.isEmpty(courseStudents)) {
			return CourseAvgProgressDto.builder().build();
		}

		double avgGrade = courseStudents.stream()
				.mapToDouble(StudentSubjectCourse::getFinalMark)
				.average()
				.orElse(0d);

		double avgPresences = studentLessons
				.stream()
				.collect(Collectors.groupingBy(StudentCourseLesson::getStudentId))
				.values()
				.stream()
				.mapToDouble(studentCourseLessons -> {
					double presence = studentCourseLessons.stream()
							.map(StudentCourseLesson::getPresentState)
							.filter(StudentPresence.PRESENT::equals)
							.count();
					return presence / studentCourseLessons.size() * 100;
				})
				.average()
				.orElse(0d);

		double avgInterest = studentLessons.stream()
				.map(StudentCourseLesson::getInterest)
				.filter(Objects::nonNull)
				.mapToInt(interest -> interest)
				.average().orElse(0);

		double avgRelevance = studentLessons.stream()
				.map(StudentCourseLesson::getRelevance)
				.filter(Objects::nonNull)
				.mapToInt(relevance -> relevance)
				.average().orElse(0);

		double avgInterestAndRelevance = (avgInterest + avgRelevance) * 10;

		return CourseAvgProgressDto.builder()
				.avgInterestAndRelevance(avgInterestAndRelevance)
				.avgPresence(avgPresences)
				.avgGrade(avgGrade)
				.build();
	}

	@Override
	public CourseGradesProgressDto getCourseGradesAnalysis(UUID courseId) {
		List<StudentSubjectCourse> courseStudents = studentSubjectCourseService.findAllByCourseId(courseId);

		if (CollectionUtils.isEmpty(courseStudents)) {
			return CourseGradesProgressDto.builder().build();
		}

		Map<Integer, Long> gradeToNumberOfStudents = courseStudents.stream()
				.collect(Collectors.groupingBy(StudentSubjectCourse::getFinalMark, Collectors.counting()));

		return CourseGradesProgressDto.builder()
				.grades(gradeToNumberOfStudents.entrySet()
						.stream()
						.sorted(Map.Entry.comparingByKey())
						.map(entry -> CourseGradesProgressDto.GroupedGradesDto.builder()
								.grade(entry.getKey())
								.numberOfStudents(entry.getValue())
								.build()
						).collect(Collectors.toList())
				).build();
	}

	@Override
	public CourseBarsProgressDto getCourseBarsAnalysis(UUID courseId) {
		List<StudentCourseLesson> studentLessons = studentCourseLessonService.findAllFinishedByCourseId(courseId);

		if (CollectionUtils.isEmpty(studentLessons)) {
			return CourseBarsProgressDto.builder().build();
		}

		List<CourseBarsProgressDto.GroupedBarsDto> barsProgress = studentLessons
				.stream()
				.collect(Collectors.groupingBy(
						lesson -> LocalDate.ofInstant(
								lesson.getCourseLesson().getStartDate(), ZoneId.of("Europe/Kiev")
						).withDayOfMonth(1)
				))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.map(entry -> {
					List<StudentCourseLesson> lessons = entry.getValue();

					double avgInterest = lessons.stream()
							.map(StudentCourseLesson::getInterest)
							.filter(Objects::nonNull)
							.mapToInt(interest -> interest)
							.average().orElse(0);

					double avgRelevance = lessons.stream()
							.map(StudentCourseLesson::getRelevance)
							.filter(Objects::nonNull)
							.mapToInt(relevance -> relevance)
							.average().orElse(0);

					double avgPresences = lessons
							.stream()
							.collect(Collectors.groupingBy(StudentCourseLesson::getStudentId))
							.values()
							.stream()
							.mapToDouble(studentCourseLessons -> {
								double presence = studentCourseLessons.stream()
										.map(StudentCourseLesson::getPresentState)
										.filter(StudentPresence.PRESENT::equals)
										.count();
								return presence / studentCourseLessons.size() * 100;
							})
							.average()
							.orElse(0d);

					return CourseBarsProgressDto.GroupedBarsDto.builder()
							.month(entry.getKey().format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH)))
							.avgRelevance(avgRelevance * 20)
							.avgInterest(avgInterest * 20)
							.avgPresence(avgPresences)
							.build();
				}).collect(Collectors.toList());

		return CourseBarsProgressDto.builder()
				.bars(barsProgress)
				.build();
	}

	@Override
	public CourseStudentProgressDto getCourseStudentAnalysis(UUID courseId) {
		List<StudentSubjectCourse> courseStudents = studentSubjectCourseService.findAllByCourseId(courseId);
		List<StudentCourseLesson> studentLessons = studentCourseLessonService.findAllFinishedByCourseId(courseId);

		if (CollectionUtils.isEmpty(studentLessons) || CollectionUtils.isEmpty(courseStudents)) {
			return CourseStudentProgressDto.builder().build();
		}

		UUID studentId = securityService.getUserId();

		double avgGrade = courseStudents.stream()
				.mapToDouble(StudentSubjectCourse::getFinalMark)
				.average()
				.orElse(0d);

		double avgStudentGrade = courseStudents.stream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.mapToDouble(StudentSubjectCourse::getFinalMark)
				.findFirst()
				.orElse(0d);


		double avgPresences = studentLessons
				.stream()
				.collect(Collectors.groupingBy(StudentCourseLesson::getStudentId))
				.values()
				.stream()
				.mapToDouble(studentCourseLessons -> {
					double presence = studentCourseLessons.stream()
							.map(StudentCourseLesson::getPresentState)
							.filter(StudentPresence.PRESENT::equals)
							.count();
					return presence / studentCourseLessons.size() * 100;
				})
				.average()
				.orElse(0d);

		double avgStudentPresences = studentLessons
				.stream()
				.collect(Collectors.groupingBy(StudentCourseLesson::getStudentId))
				.entrySet()
				.stream()
				.filter(studentIdGrouped -> studentId.equals(studentIdGrouped.getKey()))
				.findFirst()
				.map(Map.Entry::getValue)
				.stream()
				.mapToDouble(studentCourseLessons -> {
					double presence = studentCourseLessons.stream()
							.map(StudentCourseLesson::getPresentState)
							.filter(StudentPresence.PRESENT::equals)
							.count();
					return presence / studentCourseLessons.size() * 100;
				})
				.average()
				.orElse(0d);

		return CourseStudentProgressDto.builder()
				.bars(List.of(
						CourseStudentProgressDto.GroupedStudentBarsDto.builder()
								.title("your")
								.avgGrade(avgStudentGrade)
								.avgPresence(avgStudentPresences)
								.build(),
						CourseStudentProgressDto.GroupedStudentBarsDto.builder()
								.title("others")
								.avgGrade(avgGrade)
								.avgPresence(avgPresences)
								.build()
				))
				.build();
	}

	@Override
	public StudentCourseGradesDto getCourseStudentGradesAnalysis(UUID courseId) {
		UUID studentId = securityService.getUserId();
		List<StudentCourseLesson> studentLessons = studentCourseLessonService.findAllFinishedByCourseIdAndStudentId(
				courseId, studentId
		);

		if (CollectionUtils.isEmpty(studentLessons)) {
			return StudentCourseGradesDto.builder().build();
		}

		List<StudentCourseGradesDto.StudentCourseGradeDto> grades = studentLessons
				.stream()
				.collect(Collectors.groupingBy(lesson -> LocalDate.ofInstant(
						lesson.getCourseLesson().getStartDate(), ZoneId.of("Europe/Kiev")
				)))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.map(entry -> StudentCourseGradesDto.StudentCourseGradeDto.builder()
						.date(entry.getKey())
						.grade(entry.getValue().stream().mapToDouble(StudentCourseLesson::getMark).sum())
						.build()
				)
				.filter(progress -> progress.getGrade() > 0)
				.collect(Collectors.toList());

		return StudentCourseGradesDto.builder()
				.grades(grades)
				.build();
	}

	public static double correlationCoefficient(List<StudentCourseProgressDto> students) {
		int n = students.size();
		double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

		for (StudentCourseProgressDto s : students) {
			double avgGrade = s.getGrade();
			double avgAttendance = s.getPresence();

			sumX += avgGrade;
			sumY += avgAttendance;
			sumXY += avgGrade * avgAttendance;
			sumX2 += avgGrade * avgGrade;
			sumY2 += avgAttendance * avgAttendance;
		}

		return (n * sumXY - sumX * sumY) /
			   (Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY)));
	}

	public static double[] linearRegression(List<StudentCourseProgressDto> students) {
		int n = students.size();
		double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

		for (StudentCourseProgressDto s : students) {
			double avgGrade = s.getGrade();
			double avgAttendance = s.getPresence();

			sumX += avgAttendance;
			sumY += avgGrade;
			sumXY += avgAttendance * avgGrade;
			sumX2 += avgAttendance * avgAttendance;
		}

		double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
		double intercept = (sumY - slope * sumX) / n;

		return new double[]{slope, intercept};
	}

}
