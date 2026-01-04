package com.marketplace.vendor.service;

import com.marketplace.entity.Role;
import com.marketplace.entity.User;
import com.marketplace.exception.BadRequestException;
import com.marketplace.repository.UserRepository;
import com.marketplace.vendor.dto.VendorCreateRequest;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.entity.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class VendorService {

	private final VendorRepository vendorRepository;
	private final UserRepository userRepository;

	@Transactional
	public Vendor createVendor(String adminEmail, VendorCreateRequest request) {

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (vendorRepository.existsByUser(user)) {
			throw new RuntimeException("User already has a vendor");
		}

		Vendor vendor = Vendor.builder()
				.name(request.getName())
				.user(user)               // 🔥 REQUIRED
				.status(VendorStatus.PENDING)
				.build();

		return vendorRepository.save(vendor);
	}

	@Transactional
	public Vendor approveVendor(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId)
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		vendor.setStatus(VendorStatus.APPROVED);
		return vendor;
	}
}
