package com.marketplace.vendor.controller;

import com.marketplace.vendor.dto.VendorStatusResponse;
import com.marketplace.vendor.enums.VendorStatus;
import com.marketplace.vendor.service.VendorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/vendors")
@RequiredArgsConstructor
public class AdminVendorController {

	private final VendorService vendorService;

	// 🔹 Get pending vendor requests
	@GetMapping("/pending")
	public List<VendorStatusResponse> getPendingVendors() {
		return vendorService.getVendorsByStatus(VendorStatus.PENDING);
	}

	// 🔹 Get approved vendors
	@GetMapping("/active")
	public List<VendorStatusResponse> getActiveVendors() {
		return vendorService.getVendorsByStatus(VendorStatus.APPROVED);
	}

	// 🔹 Approve vendor
	@PutMapping("/{id}/approve")
	public VendorStatusResponse approve(@PathVariable Long id) {
		return vendorService.approveVendor(id);
	}

	// 🔹 Reject vendor (optional but recommended)
	@PutMapping("/{id}/reject")
	public VendorStatusResponse reject(@PathVariable Long id) {
		return vendorService.rejectVendor(id);
	}
}

