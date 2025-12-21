package com.substring.auth.security;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.substring.auth.entity.Role;
import com.substring.auth.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter

public class JwtService {

	private final CustomUserDetailService customUserDetailService;

	private final SecretKey key;
	private final long accessTtlSeconds;
	private final long refreshTtlSeconds;
	private final String issuer;

	public JwtService(@Value("${security.jwt.secret}") String secret,
			@Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
			@Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
			@Value("${security.jwt.issuer}") String issuer, CustomUserDetailService customUserDetailService) {

		if (secret == null || secret.length() < 64) {
			throw new IllegalArgumentException("Invalid Secreate");
		}

		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessTtlSeconds = accessTtlSeconds;
		this.refreshTtlSeconds = refreshTtlSeconds;
		this.issuer = issuer;
		this.customUserDetailService = customUserDetailService;
	}

	// generate token

	public String generateAccessToken(User user) {
		Instant now = Instant.now();

		List<String> roles = user.getRoles() == null ? List.of() : user.getRoles().stream().map(Role::getName).toList();

		return Jwts.builder().id(UUID.randomUUID().toString()).subject(user.getId().toString()).issuer(issuer)
				.issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
				.claim("email", user.getEmail()).claim("roles", roles).claim("typ", "access").signWith(key) // point
				.compact();
	}

	// generate refresh token

	// generate refresh token
	public String generateRefreshToken(User user, String jti) {

		Instant now = Instant.now();

		return Jwts.builder().id(jti).subject(user.getId().toString()).issuer(issuer).issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(refreshTtlSeconds))).claim("typ", "refresh") // ✅ correct
				.signWith(key) // ✅ correct for 0.12.x
				.compact();
	}

	// parse the token

	// parse the token
	public Jws<Claims> parse(String token) {
		try {
			return Jwts.parser() // 0.12.x API
					.verifyWith(key) // same key you used in signWith(...)
					.build().parseSignedClaims(token);
		} catch (JwtException e) {
			throw e;
		}
	}

	public boolean isAccessToken(String token) {
		Claims c = parse(token).getPayload();
		return "access".equals(c.get("typ", String.class));
	}

	public boolean isRefreshToken(String token) {
		Claims c = parse(token).getPayload();
		return "refresh".equals(c.get("typ", String.class));
	}

	public UUID getUserId(String token) {
		Claims c = parse(token).getPayload();
		return UUID.fromString(c.getSubject());
	}

	public String getJti(String token) {
		return parse(token).getPayload().getId();
	}

	public List<String> getRoles(String token) {
		Claims c = parse(token).getPayload();
		return (List<String>) c.get("roles");
	}

	public String getEmail(String token) {
		Claims c = parse(token).getPayload();
		return (String) c.get("email");
	}

}
