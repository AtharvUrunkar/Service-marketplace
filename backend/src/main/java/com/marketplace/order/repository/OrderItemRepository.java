package com.marketplace.order.repository;

import com.marketplace.order.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	// Vendor sees only their order items
	List<OrderItem> findByVendorId(Long vendorId);

	// Items belonging to a specific order
	List<OrderItem> findByOrderId(Long orderId);
}

