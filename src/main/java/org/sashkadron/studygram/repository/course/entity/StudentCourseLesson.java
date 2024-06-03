package org.sashkadron.studygram.repository.course.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;
import org.sashkadron.studygram.repository.course.entity.types.StudentPresence;
import org.sashkadron.studygram.repository.user.entity.Student;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "student_course_lesson")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentCourseLesson extends CommonEntity {

	@Column(name = "course_lesson_id")
	private UUID courseLessonId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_lesson_id", insertable = false, updatable = false)
	private CourseLesson courseLesson;

	@Column(name = "student_id")
	private UUID studentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", insertable = false, updatable = false)
	private Student student;

	@Builder.Default
	private Integer mark = 0;

	private Integer interest;

	private Integer relevance;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private StudentPresence presentState = StudentPresence.NONE;

	private Instant updatedPresentDate;

}
