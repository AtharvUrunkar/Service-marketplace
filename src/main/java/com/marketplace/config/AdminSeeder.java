package com.marketplace.config;

import com.marketplace.entity.Role;
import com.marketplace.entity.User;
import com.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {

		boolean adminExists = userRepository.existsByEmail("admin@test.com");

		if (!adminExists) {
			User admin = new User();
			admin.setEmail("admin@test.com");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			admin.setActive(true);

			userRepository.save(admin);

			System.out.println("✅ Default ADMIN user created");
		}
	}
}
