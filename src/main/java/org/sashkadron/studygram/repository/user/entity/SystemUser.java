package org.sashkadron.studygram.repository.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.config.db.CommonEntity;
import org.sashkadron.studygram.repository.user.entity.types.UserRole;

import java.time.Instant;

@Entity
@Table(name = "system_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SystemUser extends CommonEntity {

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	@NotBlank
	@Email(message = "User has invalid email")
	private String email;

	@Builder.Default
	private Boolean isEmailVerified = false;

	private String phone;

	@NotNull
	private String password;

	@Builder.Default
	private Instant createdDate = Instant.now();

	public boolean isStudent() {
		return UserRole.ROLE_STUDENT.equals(this.role);
	}
	public boolean isLector() {
		return UserRole.ROLE_LECTOR.equals(this.role);
	}

}
