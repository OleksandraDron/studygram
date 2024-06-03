package org.sashkadron.studygram.controller.user.facade.impl;

import lombok.AllArgsConstructor;
import org.sashkadron.studygram.config.exception.AppException;
import org.sashkadron.studygram.config.security.service.JwtTokenAuthService;
import org.sashkadron.studygram.config.security.service.SecurityService;
import org.sashkadron.studygram.controller.user.dto.LogInDto;
import org.sashkadron.studygram.controller.user.dto.UserDto;
import org.sashkadron.studygram.controller.user.dto.UserLogInDto;
import org.sashkadron.studygram.controller.user.facade.UserFacade;
import org.sashkadron.studygram.controller.user.facade.mapper.UserMapper;
import org.sashkadron.studygram.repository.user.entity.Lecturer;
import org.sashkadron.studygram.repository.user.entity.Student;
import org.sashkadron.studygram.repository.user.entity.SystemUser;
import org.sashkadron.studygram.service.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFacadeImpl implements UserFacade {

	private final JwtTokenAuthService jwtTokenAuthService;
	private final PasswordEncoder passwordEncoder;
	private final SecurityService securityService;

	private final UserService userService;
	private final UserMapper userMapper;

	@Override
	public UserLogInDto logInUser(LogInDto logInDto) {
		SystemUser user = userService.findByEmail(logInDto.getEmail());
		if (!passwordEncoder.matches(logInDto.getPassword(), user.getPassword())) {
			throw new AppException("Invalid password");
		}

		return userMapper.toUserLogInDto(user, jwtTokenAuthService.issueAccessToken(user.getEmail()));
	}

	@Override
	public UserDto getAuthUser() {
		SystemUser user = securityService.getUser();

		if (user.isLector()) {
			return userMapper.toLecturerDto((Lecturer) user);
		}

		return userMapper.toStudentDto((Student) user);
	}
}
