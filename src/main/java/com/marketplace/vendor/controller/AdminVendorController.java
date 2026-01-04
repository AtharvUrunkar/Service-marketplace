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
	public Vendor createVendor(
			@AuthenticationPrincipal UserDetails admin,
			@RequestBody @Valid VendorCreateRequest request
	) {
		return vendorService.createVendor(admin.getUsername(), request);
	}

	@PutMapping("/{id}/approve")
	public Vendor approveVendor(@PathVariable Long id) {
		return vendorService.approveVendor(id);
	}
}
