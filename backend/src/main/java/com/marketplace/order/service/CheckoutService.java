package com.marketplace.order.service;

import com.marketplace.cart.entity.Cart;
import com.marketplace.cart.entity.CartItem;
import com.marketplace.cart.repository.CartRepository;
import com.marketplace.entity.User;
import com.marketplace.order.dto.OrderResponse;
import com.marketplace.order.entity.Order;
import com.marketplace.order.entity.OrderItem;
import com.marketplace.order.enums.OrderStatus;
import com.marketplace.order.repository.OrderRepository;
import com.marketplace.product.entity.Product;
import com.marketplace.product.enums.ProductStatus;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	// =====================================
	// CHECKOUT
	// =====================================
	@Transactional
	public OrderResponse checkout(String email) {

		// 1️⃣ Load user
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 2️⃣ Load cart
		Cart cart = cartRepository.findByUser(user)
				.orElseThrow(() -> new RuntimeException("Cart not found"));

		if (cart.getItems().isEmpty()) {
			throw new IllegalStateException("Cart is empty");
		}

		// 3️⃣ Prepare order
		Order order = Order.builder()
				.user(user)
				.status(OrderStatus.PENDING_PAYMENT)
				.createdAt(LocalDateTime.now())
				.build();

		List<OrderItem> orderItems = new ArrayList<>();
		BigDecimal totalAmount = BigDecimal.ZERO;

		// 4️⃣ Validate & snapshot each cart item
		for (CartItem cartItem : cart.getItems()) {

			Product product = productRepository.findById(cartItem.getProduct().getId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			if (product.getStatus() != ProductStatus.APPROVED) {
				throw new IllegalStateException(
						"Product not approved: " + product.getName()
				);
			}

			BigDecimal itemTotal =
					product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

			totalAmount = totalAmount.add(itemTotal);

			OrderItem orderItem = OrderItem.builder()
					.order(order)
					.productId(product.getId())
					.productName(product.getName())
					.priceAtPurchase(product.getPrice())
					.quantity(cartItem.getQuantity())
					.vendorId(product.getVendor().getId())
					.build();

			orderItems.add(orderItem);
		}

		// 5️⃣ Finalize order
		order.setTotalAmount(totalAmount);
		order.setItems(orderItems);

		Order savedOrder = orderRepository.save(order);

		// 6️⃣ Clear cart
		cart.getItems().clear();

		// 7️⃣ Return response
		return new OrderResponse(
				savedOrder.getId(),
				savedOrder.getTotalAmount(),
				savedOrder.getStatus().name()
		);
	}
}
