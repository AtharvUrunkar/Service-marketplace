package com.marketplace.order.dto;

import java.math.BigDecimal;

public record OrderResponse(
		Long orderId,
		BigDecimal totalAmount,
		String status
) {}
