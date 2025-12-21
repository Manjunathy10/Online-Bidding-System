package com.substring.auth.exception;

import javax.security.auth.login.CredentialException;
import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.substring.auth.dtos.ApiError;
import com.substring.auth.dtos.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class, CredentialException.class,
			ExpiredJwtException.class, JwtException.class, AuthenticationException.class

	})
	public ResponseEntity<ApiError> handleAuthException(Exception e, HttpServletRequest request) {
		
		logger.info("Exception : {}",e.getClass().getName());

		var apiError = ApiError.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.badRequest().body(apiError);
	}

	// Recourse not found exception handler method
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResoureNotFoundException(ResourceNotFoundException exception) {
		ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
	}

	// email is required
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
		ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
	}

}
