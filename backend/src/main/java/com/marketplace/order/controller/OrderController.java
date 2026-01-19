package com.marketplace.order.controller;

import com.marketplace.order.dto.OrderResponse;
import com.marketplace.order.entity.Order;
import com.marketplace.order.service.CheckoutService;
import com.marketplace.order.repository.OrderRepository;
import com.marketplace.entity.User;
import com.marketplace.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final CheckoutService checkoutService;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	// =====================================
	// CHECKOUT
	// =====================================
	@PostMapping("/checkout")
	public OrderResponse checkout(
			@AuthenticationPrincipal UserDetails user
	) {
		return checkoutService.checkout(user.getUsername());
	}

	// =====================================
	// USER: VIEW OWN ORDERS
	// =====================================
	@GetMapping
	public List<Order> getMyOrders(
			@AuthenticationPrincipal UserDetails user
	) {
		User currentUser = userRepository.findByEmail(user.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		return orderRepository.findByUser(currentUser);
	}
}
