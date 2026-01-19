package com.marketplace.product.service;

import com.marketplace.entity.User;
import com.marketplace.product.dto.AdminProductCreateRequest;
import com.marketplace.product.dto.ProductCreateRequest;
import com.marketplace.product.dto.ProductStatusResponse;
import com.marketplace.product.entity.Product;
import com.marketplace.product.enums.ProductStatus;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.repository.UserRepository;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.enums.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final VendorRepository vendorRepository;
	private final UserRepository userRepository;

	// =================================================
	// ADMIN creates product (auto-approved)
	// =================================================
	@Transactional
	public ProductStatusResponse createProductAsAdmin(AdminProductCreateRequest request) {

		Vendor vendor = vendorRepository.findById(request.getVendorId())
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		if (vendor.getStatus() != VendorStatus.APPROVED) {
			throw new RuntimeException("Vendor not approved");
		}

		Product product = Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.status(ProductStatus.APPROVED) // admin-created = approved
				.vendor(vendor)
				.build();

		Product saved = productRepository.save(product);

		return new ProductStatusResponse(
				saved.getId(),
				saved.getStatus().name()
		);
	}

	// =================================================
	// VENDOR creates product (DRAFT)
	// =================================================
	@Transactional
	public ProductStatusResponse createProduct(String email, ProductCreateRequest request) {

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

		Product saved = productRepository.save(product);

		return new ProductStatusResponse(
				saved.getId(),
				saved.getStatus().name()
		);
	}

	// =================================================
	// VENDOR submits product for admin approval
	// =================================================
	@Transactional
	public ProductStatusResponse submitForApproval(Long productId, String vendorEmail) {

		User user = userRepository.findByEmail(vendorEmail)
				.orElseThrow(() -> new RuntimeException("User not found"));

		Vendor vendor = vendorRepository.findByUser(user)
				.orElseThrow(() -> new RuntimeException("User is not a vendor"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// Ownership check
		if (!product.getVendor().getId().equals(vendor.getId())) {
			throw new SecurityException("Not your product");
		}

		if (product.getStatus() != ProductStatus.DRAFT) {
			throw new IllegalStateException("Only DRAFT products can be submitted");
		}

		product.setStatus(ProductStatus.PENDING_APPROVAL);

		return new ProductStatusResponse(
				product.getId(),
				product.getStatus().name()
		);
	}

	// =================================================
	// ADMIN approves product
	// =================================================
	@Transactional
	public ProductStatusResponse approveProduct(Long productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		if (product.getStatus() != ProductStatus.PENDING_APPROVAL) {
			throw new IllegalStateException("Product is not pending approval");
		}

		product.setStatus(ProductStatus.APPROVED);

		return new ProductStatusResponse(
				product.getId(),
				product.getStatus().name()
		);
	}
}
