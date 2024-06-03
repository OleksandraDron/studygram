package org.sashkadron.studygram.repository.course.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;
import org.sashkadron.studygram.repository.user.entity.Student;

import java.util.UUID;

@Entity
@Table(name = "student_subject_course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentSubjectCourse extends CommonEntity {

	@Column(name = "subject_course_id")
	UUID subjectCourseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_course_id", insertable = false, updatable = false)
	SubjectCourse subjectCourse;

	@Column(name = "student_id")
	UUID studentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", insertable = false, updatable = false)
	Student student;

	@Builder.Default
	Integer finalMark = 0;

}
