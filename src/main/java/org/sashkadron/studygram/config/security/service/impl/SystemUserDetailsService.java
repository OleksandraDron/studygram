package org.sashkadron.studygram.config.security.service.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.config.security.SystemUserDetails;
import org.sashkadron.studygram.repository.user.entity.SystemUser;
import org.sashkadron.studygram.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Override
	public SystemUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		SystemUser user = userService.findOptByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found by email"));

		return new SystemUserDetails(user);
	}

}
