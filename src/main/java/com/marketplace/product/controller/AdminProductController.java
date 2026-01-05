package com.marketplace.product.controller;

import com.marketplace.product.dto.AdminProductCreateRequest;
import com.marketplace.product.dto.ProductStatusResponse;
import com.marketplace.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

	private final ProductService productService;

	// =========================
	// ADMIN creates product
	// =========================
	@PostMapping
	public ProductStatusResponse createProduct(
			@Valid @RequestBody AdminProductCreateRequest request
	) {
		return productService.createProductAsAdmin(request);
	}

	// =========================
	// ADMIN approves product
	// =========================
	@PutMapping("/{id}/approve")
	public ProductStatusResponse approveProduct(@PathVariable Long id) {
		return productService.approveProduct(id);
	}
}
