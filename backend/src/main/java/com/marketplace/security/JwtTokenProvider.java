package com.marketplace.security;

import com.marketplace.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

	private static final String SECRET_KEY = "marketplace-secret-key-change-later";
	private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	// 🔑 CREATE JWT WITH ROLE
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole().name());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// 🔍 GET USERNAME
	public String getUsernameFromToken(String token) {
		return getClaims(token).getSubject();
	}

	// 🔍 GET ROLE
	public String getRoleFromToken(String token) {
		return getClaims(token).get("role", String.class);
	}

	// ✅ VALIDATE TOKEN
	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 🔧 INTERNAL CLAIM PARSER
	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
