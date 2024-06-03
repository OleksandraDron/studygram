package org.sashkadron.studygram.controller.user.facade;

import org.sashkadron.studygram.controller.user.dto.LogInDto;
import org.sashkadron.studygram.controller.user.dto.UserDto;
import org.sashkadron.studygram.controller.user.dto.UserLogInDto;

public interface UserFacade {

	UserLogInDto logInUser(LogInDto logInDto);

	UserDto getAuthUser();
}
