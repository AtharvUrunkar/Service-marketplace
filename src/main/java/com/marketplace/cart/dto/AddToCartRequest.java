package com.marketplace.cart.dto;

public record AddToCartRequest(
		Long productId,
		int quantity
) {}
