package com.marketplace.cart.repository;

import com.marketplace.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	// Prevent duplicate cart items
	Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

	void deleteByCartId(Long cartId);
}
