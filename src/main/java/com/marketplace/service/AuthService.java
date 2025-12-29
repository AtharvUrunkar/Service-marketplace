package com.marketplace.service;

import com.marketplace.dto.AuthResponse;
import com.marketplace.dto.LoginRequest;
import com.marketplace.entity.User;
import com.marketplace.repository.UserRepository;
import com.marketplace.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.marketplace.dto.RegisterRequest;
import com.marketplace.entity.Role;


@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	public AuthResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		// 🔥 LOAD UserDetails (IMPORTANT)
		UserDetails userDetails =
				userDetailsService.loadUserByUsername(user.getEmail());

		// 🔐 Generate token using UserDetails
		String token = jwtTokenProvider.generateToken(userDetails);

		return new AuthResponse(token);
	}
	public void register(RegisterRequest request) {

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already registered");
		}

		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.CUSTOMER)   // default role
				.active(true)
				.build();

		userRepository.save(user);
	}

}
