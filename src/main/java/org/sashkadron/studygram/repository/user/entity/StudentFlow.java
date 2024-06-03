package org.sashkadron.studygram.repository.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;

@Entity
@Table(name = "student_flow")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentFlow extends CommonEntity {

	String title;

	String flowCode;

}
