package com.marketplace.payment.entity;

import com.marketplace.entity.User;
import com.marketplace.order.entity.Order;
import com.marketplace.payment.enums.PaymentMethod;
import com.marketplace.payment.enums.PaymentStatus;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

	// 🔗 Who paid
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	// 🔗 Which order
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id")
	private Order order;

	// 💰 Amount
	@Column(nullable = false)
	private BigDecimal amount;

	// 🧾 Gateway reference (Razorpay payment_id)
	@Column(unique = true)
	private String gatewayPaymentId;

	// 📌 Status
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;

	// ❌ Failure reason (for FAILED payments)
	private String failureReason;

	// ⏱ Audit
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod paymentMethod;

}
