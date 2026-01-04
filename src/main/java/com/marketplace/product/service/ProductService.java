package com.marketplace.product.service;

import com.marketplace.entity.User;
import com.marketplace.product.dto.ProductCreateRequest;
import com.marketplace.product.entity.Product;
import com.marketplace.product.entity.ProductStatus;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.repository.UserRepository;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.entity.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;
import com.marketplace.product.dto.AdminProductCreateRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final VendorRepository vendorRepository;
	private final UserRepository userRepository;

	// =========================
	// ADMIN creates product
	// =========================
	@Transactional
	public Product createProductAsAdmin(AdminProductCreateRequest request) {

		Vendor vendor = vendorRepository.findById(request.getVendorId())
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		if (vendor.getStatus() != VendorStatus.APPROVED) {
			throw new RuntimeException("Vendor not approved");
		}

		Product product = Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.status(ProductStatus.ACTIVE)
				.vendor(vendor)
				.build();

		return productRepository.save(product);
	}


	// =========================
	// VENDOR creates product
	// =========================
	@Transactional
	public Product createProduct(String email, ProductCreateRequest request) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Vendor vendor = vendorRepository.findByUser(user)
				.orElseThrow(() -> new RuntimeException("User is not a vendor"));

		if (vendor.getStatus() != VendorStatus.APPROVED) {
			throw new RuntimeException("Vendor not approved");
		}

		Product product = Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.status(ProductStatus.DRAFT)
				.vendor(vendor)
				.build();

		return productRepository.save(product);
	}
	@Transactional
	public Product approveProduct(Long productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		product.setStatus(ProductStatus.ACTIVE);

		return productRepository.save(product);
	}

}
