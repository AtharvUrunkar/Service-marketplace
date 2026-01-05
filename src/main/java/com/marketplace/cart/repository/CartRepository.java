package com.marketplace.cart.repository;

import com.marketplace.cart.entity.Cart;
import com.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

	// One active cart per user
	Optional<Cart> findByUser(User user);

	// Useful for auth-based access
	Optional<Cart> findByUserId(Long userId);

	boolean existsByUserId(Long userId);
}
