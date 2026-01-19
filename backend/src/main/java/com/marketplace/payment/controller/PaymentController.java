package com.marketplace.payment.controller;

import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.enums.PaymentMethod;
import com.marketplace.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	// =========================
	// INITIATE PAYMENT
	// =========================
	@PostMapping("/initiate")
	public Payment initiatePayment(
			@RequestParam Long orderId,
			@RequestParam PaymentMethod method,
			@AuthenticationPrincipal UserDetails user
	) {
		return paymentService.initiatePayment(
				orderId,
				user.getUsername(),
				method
		);
	}

	// =========================
	// PAYMENT SUCCESS (Webhook / Admin / Gateway callback)
	// =========================
	@PutMapping("/{paymentId}/success")
	public void paymentSuccess(@PathVariable Long paymentId) {
		paymentService.markPaymentSuccess(paymentId);
	}

	// =========================
	// PAYMENT FAILURE
	// =========================
	@PutMapping("/{paymentId}/failed")
	public void paymentFailed(
			@PathVariable Long paymentId,
			@RequestParam String reason
	) {
		paymentService.markPaymentFailed(paymentId, reason);
	}
}
