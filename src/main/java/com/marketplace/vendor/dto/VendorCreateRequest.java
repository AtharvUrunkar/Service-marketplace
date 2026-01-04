package com.marketplace.vendor.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorCreateRequest {

	@NotNull
	private Long userId;   // 👈 important

	@NotBlank
	private String name;
}
