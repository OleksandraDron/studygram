package org.sashkadron.studygram.config.security.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.sashkadron.studygram.config.exception.AppException;
import org.sashkadron.studygram.config.security.service.JwtTokenAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtTokenAuthServiceImpl implements JwtTokenAuthService {

	private final SecretKey accessTokenKey;

	public JwtTokenAuthServiceImpl(@Value("${access-token-key}") String accessTokenKey) {
		this.accessTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenKey));
	}

	@Override
	public Jws<Claims> parseJwtToken(String token) {
		try {
			return Jwts.parser()
					.verifyWith(accessTokenKey)
					.build()
					.parseSignedClaims(token);
		} catch (JwtException | IllegalArgumentException e) {
			throw new AppException("Access token is invalid");
		}
	}

	@Override
	public void validateJwsClaims(Jws<Claims> jwsClaims) {
		boolean isTokenExpired = new Date().after(jwsClaims.getPayload().getExpiration());

		if (isTokenExpired) {
			throw new AppException("Access token is expired");
		}
	}

	@Override
	public String issueAccessToken(String subject) {
		return Jwts
				.builder()
				.expiration(Date.from(Instant.now().plus(3, ChronoUnit.DAYS)))
				.issuedAt(new Date(System.currentTimeMillis()))
				.signWith(accessTokenKey)
				.subject(subject)
				.compact();
	}

}
