package org.sashkadron.studygram.controller.user.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sashkadron.studygram.repository.user.entity.types.UserRole;

import java.util.UUID;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private UUID id;

	private UserRole role;

	private String firstname;

	private String lastname;

	@Email(message = "Invalid email address")
	private String email;

	private Boolean isEmailVerified;

	private String phone;

}
