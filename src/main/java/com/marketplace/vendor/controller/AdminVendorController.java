package com.marketplace.vendor.controller;

import com.marketplace.vendor.dto.VendorStatusResponse;
import com.marketplace.vendor.service.VendorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/vendors")
@RequiredArgsConstructor
public class AdminVendorController {

	private final VendorService vendorService;

	@PutMapping("/{id}/approve")
	public VendorStatusResponse approve(@PathVariable Long id) {
		return vendorService.approveVendor(id);
	}
}
