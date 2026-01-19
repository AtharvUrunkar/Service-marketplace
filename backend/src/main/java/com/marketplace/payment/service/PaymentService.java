package com.marketplace.payment.service;

import com.marketplace.entity.User;
import com.marketplace.order.entity.Order;
import com.marketplace.order.enums.OrderStatus;
import com.marketplace.order.repository.OrderRepository;
import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.enums.PaymentMethod;
import com.marketplace.payment.enums.PaymentStatus;
import com.marketplace.payment.repository.PaymentRepository;
import com.marketplace.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	// =========================
	// INITIATE PAYMENT
	// =========================
	@Transactional
	public Payment initiatePayment(Long orderId, String userEmail, PaymentMethod method) {

		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));

		if (!order.getUser().getId().equals(user.getId())) {
			throw new SecurityException("Not your order");
		}

		if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
			throw new IllegalStateException("Order not eligible for payment");
		}

		boolean alreadyPaid = paymentRepository.existsByOrderIdAndStatus(
				order.getId(),
				PaymentStatus.SUCCESS
		);

		if (alreadyPaid) {
			throw new RuntimeException("Order already paid");
		}

		Payment payment = Payment.builder()
				.order(order)
				.user(user)
				.amount(order.getTotalAmount())
				.paymentMethod(method)
				.status(PaymentStatus.INITIATED)
				.build();

		return paymentRepository.save(payment);
	}

	// =========================
	// PAYMENT SUCCESS
	// =========================
	@Transactional
	public void markPaymentSuccess(Long paymentId) {

		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new RuntimeException("Payment not found"));

		if (payment.getStatus() != PaymentStatus.INITIATED) {
			throw new IllegalStateException("Invalid payment state");
		}

		payment.setStatus(PaymentStatus.SUCCESS);

		Order order = payment.getOrder();
		order.setStatus(OrderStatus.PAID);

		paymentRepository.save(payment);
		orderRepository.save(order);
	}

	// =========================
	// PAYMENT FAILURE
	// =========================
	@Transactional
	public void markPaymentFailed(Long paymentId, String reason) {

		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new RuntimeException("Payment not found"));

		if (payment.getStatus() != PaymentStatus.INITIATED) {
			throw new IllegalStateException("Invalid payment state");
		}

		payment.setStatus(PaymentStatus.FAILED);
		payment.setFailureReason(reason);

		Order order = payment.getOrder();
		order.setStatus(OrderStatus.FAILED);

		paymentRepository.save(payment);
		orderRepository.save(order);
	}
}
