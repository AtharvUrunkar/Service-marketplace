package com.marketplace.product.controller;

import com.marketplace.product.dto.ProductCreateRequest;
import com.marketplace.product.entity.Product;
import com.marketplace.product.service.ProductService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

	private final ProductService productService;

	@PostMapping
	public Product createProduct(@RequestBody @Valid ProductCreateRequest request) {
		return productService.createProduct(request);
	}
}
