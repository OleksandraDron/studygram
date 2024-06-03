package org.sashkadron.studygram.service.course;

import org.sashkadron.studygram.repository.course.entity.CourseLesson;
import org.sashkadron.studygram.service.course.filter.LessonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CourseLessonService {

	List<CourseLesson> saveAll(Collection<CourseLesson> courseLessons);

	void deleteByCourseIdAndLessonId(UUID courseId, UUID lessonId);

	Page<CourseLesson> findAllByCourseId(UUID courseId, Pageable pageable);

	List<CourseLesson> findAllByStudentIdAndStartDateInRange(UUID studentId, Instant startDate, Instant endDate);

	CourseLesson findById(UUID lessonId);

	Page<CourseLesson> findAllByFilter(LessonFilter lessonFilter, Pageable pageable);

	long countFinishedByCourseId(UUID courseId);

}
