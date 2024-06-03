package org.sashkadron.studygram.repository.course.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "course_lesson")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CourseLesson extends CommonEntity {

	@Column(name = "subject_course_id")
	UUID subjectCourseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_course_id", insertable = false, updatable = false)
	SubjectCourse subjectCourse;

	String title;

	String description;

	Integer duration;

	Instant startDate;

	Instant endDate;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "courseLesson", fetch = FetchType.LAZY)
	Set<StudentCourseLesson> studentLessons;

}
