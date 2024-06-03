package org.sashkadron.studygram.controller.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sashkadron.studygram.controller.user.dto.LogInDto;
import org.sashkadron.studygram.controller.user.dto.UserDto;
import org.sashkadron.studygram.controller.user.dto.UserLogInDto;
import org.sashkadron.studygram.controller.user.facade.UserFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

	private final UserFacade userFacade;

	@PostMapping("/public/users/log-in")
	public ResponseEntity<UserLogInDto> logInUser(@RequestBody @Valid LogInDto logInDto) {
		UserLogInDto response = userFacade.logInUser(logInDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private/users/auth")
	public ResponseEntity<UserDto> getAuthUser() {
		return ResponseEntity.ok(userFacade.getAuthUser());
	}

}
