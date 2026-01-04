package com.marketplace.product.repository;

import com.marketplace.product.entity.Product;
import com.marketplace.product.entity.ProductStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByStatus(ProductStatus status);
}

