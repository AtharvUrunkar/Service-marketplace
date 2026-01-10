package com.marketplace.payment.repository;

import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.enums.PaymentStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	// 🔍 Find payment by gateway reference (Razorpay ID later)
	Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

	// 🔁 All payments for an order (retry support)
	List<Payment> findByOrderId(Long orderId);

	// ✅ Successful payment for an order
	Optional<Payment> findByOrderIdAndStatus(Long orderId, PaymentStatus status);

	// ⏳ Pending / processing payments (cleanup jobs)
	List<Payment> findByStatus(PaymentStatus status);

	// 🧹 Check if order already paid
	boolean existsByOrderIdAndStatus(Long orderId, PaymentStatus status);
}
