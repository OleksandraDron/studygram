package org.sashkadron.studygram.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sashkadron.studygram.repository.user.entity.SystemUser;
import org.sashkadron.studygram.repository.user.entity.types.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserDetails implements UserDetails {

	private UUID id;

	private String email;

	private Boolean isEmailVerified;

	private String firstname;

	private String lastname;

	private String password;

	private UserRole role;

	private Collection<? extends GrantedAuthority> authorities;

	public SystemUserDetails(SystemUser user) {
		this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.password =  user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
		this.isEmailVerified = user.getIsEmailVerified();
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isStudent() {
		return UserRole.ROLE_STUDENT.equals(role);
	}

	public boolean isLector() {
		return UserRole.ROLE_LECTOR.equals(role);
	}

}
