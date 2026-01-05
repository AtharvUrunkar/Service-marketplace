package com.marketplace.vendor.service;

import com.marketplace.entity.User;
import com.marketplace.vendor.dto.VendorApplyRequest;
import com.marketplace.vendor.dto.VendorStatusResponse;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.enums.VendorStatus;
import com.marketplace.vendor.repository.VendorRepository;
import com.marketplace.repository.UserRepository;
import com.marketplace.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VendorService {

	private final VendorRepository vendorRepository;
	private final UserRepository userRepository;

	// =====================================
	// CUSTOMER applies to become vendor
	// =====================================
	@Transactional
	public VendorStatusResponse applyForVendor(String email, VendorApplyRequest request) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (user.getRole() != Role.CUSTOMER) {
			throw new IllegalStateException("Only customers can apply for vendor");
		}


		if (vendorRepository.existsByUser(user)) {
			throw new IllegalStateException("Vendor application already exists");
		}

		Vendor vendor = Vendor.builder()
				.name(request.businessName())
				.gstNumber(request.gstNumber())
				.status(VendorStatus.PENDING_APPROVAL)
				.user(user)
				.build();

		Vendor saved = vendorRepository.save(vendor);

		return new VendorStatusResponse(
				saved.getId(),
				saved.getStatus().name()
		);
	}

	// =====================================
	// ADMIN approves vendor
	// =====================================
	@Transactional
	public VendorStatusResponse approveVendor(Long vendorId) {

		Vendor vendor = vendorRepository.findById(vendorId)
				.orElseThrow(() -> new RuntimeException("Vendor not found"));

		if (vendor.getStatus() != VendorStatus.PENDING_APPROVAL) {
			throw new IllegalStateException("Vendor is not pending approval");
		}

		vendor.setStatus(VendorStatus.APPROVED);

		User user = vendor.getUser();
		user.setRole(Role.VENDOR);
		// 🔥 ROLE CHANGE ONLY HERE

		return new VendorStatusResponse(
				vendor.getId(),
				vendor.getStatus().name()
		);
	}
}
