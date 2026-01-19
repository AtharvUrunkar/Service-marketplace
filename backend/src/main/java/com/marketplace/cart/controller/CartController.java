package com.marketplace.cart.controller;

import com.marketplace.cart.dto.AddToCartRequest;
import com.marketplace.cart.dto.CartItemResponse;
import com.marketplace.cart.dto.CartResponse;
import com.marketplace.cart.dto.UpdateCartItemRequest;
import com.marketplace.cart.entity.Cart;
import com.marketplace.cart.entity.CartItem;
import com.marketplace.cart.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	// ---------------- ADD TO CART ----------------
	@PostMapping("/items")
	public void addToCart(
			@AuthenticationPrincipal(expression = "id") Long userId,
			@RequestBody AddToCartRequest request
	) {
		cartService.addToCart(userId, request.productId(), request.quantity());
	}

	// ---------------- UPDATE QUANTITY ----------------
	@PutMapping("/items/{cartItemId}")
	public void updateQuantity(
			@AuthenticationPrincipal(expression = "id") Long userId,
			@PathVariable Long cartItemId,
			@RequestBody UpdateCartItemRequest request
	) {
		cartService.updateQuantity(userId, cartItemId, request.quantity());
	}

	// ---------------- REMOVE ITEM ----------------
	@DeleteMapping("/items/{cartItemId}")
	public void removeItem(
			@AuthenticationPrincipal(expression = "id") Long userId,
			@PathVariable Long cartItemId
	) {
		cartService.removeItem(userId, cartItemId);
	}

	// ---------------- VIEW CART ----------------
	@GetMapping
	public CartResponse viewCart(
			@AuthenticationPrincipal(expression = "id") Long userId
	) {
		Cart cart = cartService.getCart(userId);

		List<CartItemResponse> items = cart.getItems().stream()
				.map(this::mapToItemResponse)
				.toList();

		BigDecimal total = items.stream()
				.map(i -> i.priceAtAddTime().multiply(BigDecimal.valueOf(i.quantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new CartResponse(cart.getId(), items, total);
	}

	// ---------------- MAPPER ----------------
	private CartItemResponse mapToItemResponse(CartItem item) {
		return new CartItemResponse(
				item.getId(),
				item.getProduct().getId(),
				item.getProduct().getName(),
				item.getVendor().getId(),
				item.getVendor().getName(),
				item.getQuantity(),
				item.getPriceAtAddTime()
		);
	}
}
