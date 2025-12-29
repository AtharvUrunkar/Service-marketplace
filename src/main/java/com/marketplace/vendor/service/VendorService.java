package com.marketplace.vendor.service;

import com.marketplace.entity.Role;
import com.marketplace.entity.User;
import com.marketplace.exception.BadRequestException;
import com.marketplace.repository.UserRepository;
import com.marketplace.vendor.dto.VendorCreateRequest;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.entity.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class VendorService {

	private final VendorRepository vendorRepository;

	public Vendor createVendor(VendorCreateRequest request) {
		Vendor vendor = Vendor.builder()
				.name(request.getName())
				.status(VendorStatus.PENDING)
				.build();

		return vendorRepository.save(vendor);
	}

	public Vendor approveVendor(Long id) {
		Vendor vendor = vendorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		vendor.setStatus(VendorStatus.APPROVED);
		return vendorRepository.save(vendor);
	}

	public Vendor blockVendor(Long id) {
		Vendor vendor = vendorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		vendor.setStatus(VendorStatus.BLOCKED);
		return vendorRepository.save(vendor);
	}
}
