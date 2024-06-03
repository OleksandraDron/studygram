package org.sashkadron.studygram.repository.course.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;
import org.sashkadron.studygram.repository.subject.entity.SubjectEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "subject_course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SubjectCourse extends CommonEntity {

	String title;

	@Column(name = "subject_id")
	UUID subjectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id", insertable = false, updatable = false)
	SubjectEntity subject;

	Instant startDate;

	Instant endDate;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "subjectCourse", fetch = FetchType.LAZY)
	Set<StudentSubjectCourse> studentSubjectCourse;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "subjectCourse", fetch = FetchType.LAZY)
	Set<LectureSubjectCourse> teachers;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "subjectCourse", fetch = FetchType.LAZY)
	Set<CourseLesson> lessons;

}
