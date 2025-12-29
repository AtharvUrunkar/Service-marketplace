package com.marketplace.vendor.controller;

import com.marketplace.vendor.dto.VendorCreateRequest;
import com.marketplace.vendor.entity.Vendor;
import com.marketplace.vendor.service.VendorService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/vendors")
@RequiredArgsConstructor
public class AdminVendorController {

	private final VendorService vendorService;

	@PostMapping
	public Vendor createVendor(@RequestBody @Valid VendorCreateRequest request) {
		return vendorService.createVendor(request);
	}

	@PutMapping("/{id}/approve")
	public Vendor approveVendor(@PathVariable Long id) {
		return vendorService.approveVendor(id);
	}

	@PutMapping("/{id}/block")
	public Vendor blockVendor(@PathVariable Long id) {
		return vendorService.blockVendor(id);
	}
}
