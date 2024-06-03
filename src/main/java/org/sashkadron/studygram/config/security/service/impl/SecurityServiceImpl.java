package org.sashkadron.studygram.config.security.service.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.config.exception.NotFoundException;
import org.sashkadron.studygram.config.security.SystemUserDetails;
import org.sashkadron.studygram.config.security.service.SecurityService;
import org.sashkadron.studygram.repository.user.entity.SystemUser;
import org.sashkadron.studygram.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

	private final UserService userService;

	@Override
	public SystemUserDetails getUserDetails() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(authentication -> authentication.getPrincipal() instanceof SystemUserDetails)
                .map(authentication -> (SystemUserDetails) authentication.getPrincipal())
                .orElseThrow(() -> new NotFoundException("Authentication not found!"));
	}

	@Override
	public UUID getUserId() {
		return getUserDetails().getId();
	}

	@Override
	public SystemUser getUser() {
		return userService.findById(getUserId());
	}

}
