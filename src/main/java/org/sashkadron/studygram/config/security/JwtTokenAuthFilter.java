package org.sashkadron.studygram.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.sashkadron.studygram.config.exception.AppException;
import org.sashkadron.studygram.config.exception.ErrorResponse;
import org.sashkadron.studygram.config.security.service.JwtTokenAuthService;
import org.sashkadron.studygram.config.security.service.impl.SystemUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenAuthFilter extends OncePerRequestFilter {

	private final SystemUserDetailsService systemUserDetailsService;
	private final JwtTokenAuthService JwtTokenAuthService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getHeader("Authorization") == null || request.getRequestURI().startsWith("/api/v1/public/");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
			Jws<Claims> claimsJws = JwtTokenAuthService.parseJwtToken(jwtToken);
			JwtTokenAuthService.validateJwsClaims(claimsJws);

			SystemUserDetails userDetails = systemUserDetailsService.loadUserByUsername(claimsJws.getPayload().getSubject());
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities()
			));
		} catch (AppException exception) {
			SecurityContextHolder.clearContext();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getWriter(), new ErrorResponse(
					exception.getMessage(),
					System.currentTimeMillis(),
					HttpStatus.UNAUTHORIZED.value()
			));
			return;
		}

		filterChain.doFilter(request, response);
	}

}
