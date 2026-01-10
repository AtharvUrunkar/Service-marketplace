package com.marketplace.order.repository;

import com.marketplace.order.entity.Order;
import com.marketplace.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// Fetch all orders placed by a user
	List<Order> findByUser(User user);

	// Optional: fetch orders by status (admin use)
	List<Order> findByStatus(Enum status);
}
