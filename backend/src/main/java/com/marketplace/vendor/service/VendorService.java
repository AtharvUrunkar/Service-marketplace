package com.marketplace.vendor.service;

import com.marketplace.entity.Role;
import com.marketplace.entity.User;
import com.marketplace.repository.UserRepository;
import com.marketplace.vendor.dto.VendorApplyRequest;
import com.marketplace.vendor.dto.VendorStatusResponse;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.enums.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VendorService {

	private final VendorRepository vendorRepository;
	private final UserRepository userRepository;

	// =================================================
	// CUSTOMER applies to become a vendor
	// =================================================
	@Transactional
	public VendorStatusResponse applyForVendor(String email, VendorApplyRequest request) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Only CUSTOMER can apply
		if (user.getRole() != Role.CUSTOMER) {
			throw new IllegalStateException("Only customers can apply for vendor");
		}

		// Prevent duplicate application
		if (vendorRepository.existsByUser(user)) {
			throw new IllegalStateException("Vendor application already exists");
		}

		Vendor vendor = Vendor.builder()
				.name(request.businessName())
				.gstNumber(request.gstNumber())
				.status(VendorStatus.PENDING)
				.user(user)
				.build();

		Vendor savedVendor = vendorRepository.save(vendor);

		return new VendorStatusResponse(
				savedVendor.getId(),
				savedVendor.getStatus().name()
		);
	}

	// =================================================
	// CUSTOMER checks vendor application status
	// =================================================
	@Transactional(readOnly = true)
	public VendorStatusResponse getVendorStatus(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		return vendorRepository.findByUser(user)
				.map(vendor -> new VendorStatusResponse(
						vendor.getId(),
						vendor.getStatus().name()
				))
				// No vendor application yet
				.orElseGet(() -> new VendorStatusResponse(
						null,
						VendorStatus.REJECTED.name()
				));
	}

	// =================================================
	// ADMIN approves vendor application
	// =================================================
	@Transactional
	public VendorStatusResponse approveVendor(Long vendorId) {

		Vendor vendor = vendorRepository.findById(vendorId)
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		if (vendor.getStatus() != VendorStatus.PENDING) {
			throw new IllegalStateException("Vendor is not pending approval");
		}

		// Approve vendor
		vendor.setStatus(VendorStatus.APPROVED);

		// Promote USER → VENDOR
		User user = vendor.getUser();
		user.setRole(Role.VENDOR);

		return new VendorStatusResponse(
				vendor.getId(),
				vendor.getStatus().name()
		);
	}
}
