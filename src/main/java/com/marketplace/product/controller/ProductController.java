package com.marketplace.product.controller;

import com.marketplace.product.dto.ProductCreateRequest;
import com.marketplace.product.entity.Product;
import com.marketplace.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public Product createProduct(
			@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody ProductCreateRequest request
	) {
		return productService.createProduct(user.getUsername(), request);
	}

}
