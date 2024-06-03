package org.sashkadron.studygram.repository.course.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;
import org.sashkadron.studygram.repository.user.entity.Lecturer;

import java.util.UUID;

@Entity
@Table(name = "lecturer_subject_course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LectureSubjectCourse extends CommonEntity {

	@Column(name = "subject_course_id")
	UUID subjectCourseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_course_id", insertable = false, updatable = false)
	SubjectCourse subjectCourse;

	@Column(name = "lecturer_id")
	UUID lecturerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecturer_id", insertable = false, updatable = false)
	Lecturer lecturer;

	String position;

}
