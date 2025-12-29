package com.marketplace.vendor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorCreateRequest {

	@NotBlank
	private String name;
}
