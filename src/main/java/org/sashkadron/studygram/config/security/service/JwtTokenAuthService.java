package org.sashkadron.studygram.config.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtTokenAuthService {

	Jws<Claims> parseJwtToken(String token);

	void validateJwsClaims(Jws<Claims> jwsClaims);

	String issueAccessToken(String subject);

}
