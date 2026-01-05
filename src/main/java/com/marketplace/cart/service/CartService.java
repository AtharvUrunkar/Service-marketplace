package com.marketplace.cart.service;

import com.marketplace.cart.entity.Cart;
import com.marketplace.cart.entity.CartItem;
import com.marketplace.cart.repository.CartItemRepository;
import com.marketplace.cart.repository.CartRepository;
import com.marketplace.product.entity.Product;
import com.marketplace.product.enums.ProductStatus;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.entity.User;
import com.marketplace.vendor.enums.VendorStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final EntityManager entityManager;

	public CartService(
			CartRepository cartRepository,
			CartItemRepository cartItemRepository,
			ProductRepository productRepository,
			EntityManager entityManager
	) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.entityManager = entityManager;
	}

	// =========================
	// ADD PRODUCT TO CART
	// =========================
	@Transactional
	public void addToCart(Long userId, Long productId, int quantity) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be at least 1");
		}

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// -------- BUSINESS RULES --------
		if (product.getStatus() != ProductStatus.APPROVED) {
			throw new IllegalStateException("Product is not approved");
		}

		if (product.getVendor().getStatus() != VendorStatus.APPROVED) {
			throw new IllegalStateException("Vendor is not approved");
		}

		Cart cart = cartRepository.findByUserId(userId)
				.orElseGet(() -> createNewCart(userId));

		CartItem existingItem = cartItemRepository
				.findByCartIdAndProductId(cart.getId(), productId)
				.orElse(null);

		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
			return;
		}

		CartItem newItem = new CartItem();
		newItem.setCart(cart);
		newItem.setProduct(product);
		newItem.setVendor(product.getVendor());
		newItem.setQuantity(quantity);
		newItem.setPriceAtAddTime(product.getPrice());

		cart.getItems().add(newItem);
	}

	// =========================
	// UPDATE CART ITEM QUANTITY
	// =========================
	@Transactional
	public void updateQuantity(Long userId, Long cartItemId, int quantity) {

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be at least 1");
		}

		CartItem item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		if (!item.getCart().getUser().getId().equals(userId)) {
			throw new SecurityException("Not your cart");
		}

		item.setQuantity(quantity);
	}

	// =========================
	// REMOVE ITEM FROM CART
	// =========================
	@Transactional
	public void removeItem(Long userId, Long cartItemId) {

		CartItem item = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		if (!item.getCart().getUser().getId().equals(userId)) {
			throw new SecurityException("Not your cart");
		}

		cartItemRepository.delete(item);
	}

	// =========================
	// VIEW CART (SERVICE LEVEL)
	// =========================
	@Transactional
	public Cart getCart(Long userId) {
		return cartRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Cart not found"));
	}

	// =========================
	// INTERNAL HELPERS
	// =========================
	private Cart createNewCart(Long userId) {
		Cart cart = new Cart();
		cart.setUser(entityManager.getReference(User.class, userId));
		return cartRepository.save(cart);
	}
}
