package com.marketplace.product.service;

import com.marketplace.product.dto.ProductCreateRequest;
import com.marketplace.product.entity.Product;
import com.marketplace.product.entity.ProductStatus;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.entity.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final VendorRepository vendorRepository;

	@Transactional
	public Product createProduct(ProductCreateRequest request) {

		Vendor vendor = vendorRepository.findById(request.getVendorId())
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		if (vendor.getStatus() != VendorStatus.APPROVED) {
			throw new RuntimeException("Vendor is not approved");
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
}
