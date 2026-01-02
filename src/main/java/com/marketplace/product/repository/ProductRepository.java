package com.marketplace.product.repository;
import com.marketplace.product.entity.Product;
import com.marketplace.product.entity.ProductStatus;
import com.marketplace.vendor.entity.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

	public interface ProductRepository extends JpaRepository<Product, Long> {

		// Admin / internal use
		List<Product> findByVendor(Vendor vendor);

		// Public listing (future use)
		List<Product> findByStatus(ProductStatus status);
	}

