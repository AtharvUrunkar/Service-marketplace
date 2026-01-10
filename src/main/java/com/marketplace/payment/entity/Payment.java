package com.marketplace.payment.entity;

import com.marketplace.order.entity.Order;
import com.marketplace.payment.enums.PaymentMethod;
import com.marketplace.payment.enums.PaymentStatus;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 🔗 One payment belongs to one order
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(nullable = false)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod method;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;

	// Razorpay / Gateway reference (later)
	@Column(name = "gateway_payment_id")
	private String gatewayPaymentId;

	@Column(nullable = false)
	private LocalDateTime createdAt;
}
