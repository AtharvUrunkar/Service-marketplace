package com.marketplace.vendor.dto;

import jakarta.validation.constraints.NotBlank;

public record VendorApplyRequest(
		@NotBlank String businessName,
		@NotBlank String gstNumber
) {}
