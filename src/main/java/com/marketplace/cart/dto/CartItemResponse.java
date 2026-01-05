package com.marketplace.cart.dto;

import java.math.BigDecimal;

public record CartItemResponse(
		Long cartItemId,
		Long productId,
		String productName,
		Long vendorId,
		String vendorName,
		int quantity,
		BigDecimal priceAtAddTime
) {}
