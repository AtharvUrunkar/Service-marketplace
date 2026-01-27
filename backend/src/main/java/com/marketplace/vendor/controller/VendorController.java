package com.marketplace.vendor.controller;

import com.marketplace.vendor.dto.VendorApplyRequest;
import com.marketplace.vendor.dto.VendorStatusResponse;
import com.marketplace.vendor.service.VendorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorController {

	private final VendorService vendorService;

	@PostMapping("/apply")
	public VendorStatusResponse apply(
			@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody VendorApplyRequest request
	) {
		return vendorService.applyForVendor(user.getUsername(), request);
	}

	@GetMapping("/status")
	public VendorStatusResponse status(
			@AuthenticationPrincipal UserDetails user
	) {
		return vendorService.getVendorStatus(user.getUsername());
	}
}
