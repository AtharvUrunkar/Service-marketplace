package com.marketplace.order.entity;

import com.marketplace.entity.User;

import com.marketplace.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",nullable = false)
	private User user;

	@Column(nullable =false)
	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@OneToMany(
			mappedBy = "order",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<OrderItem> items;

	@Column(nullable = false,updatable = false)
	private LocalDateTime createdAt;

}

