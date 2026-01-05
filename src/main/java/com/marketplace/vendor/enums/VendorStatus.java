package com.marketplace.vendor.enums;

/**
 * Represents the lifecycle state of a vendor in the marketplace.
 */
public enum VendorStatus {

	/**
	 * Vendor has applied but is not yet reviewed by admin.
	 */
	PENDING_APPROVAL,

	/**
	 * Vendor is approved and allowed to create and sell products.
	 */
	APPROVED,

	/**
	 * Vendor is temporarily disabled (policy violation, manual admin action).
	 */
	SUSPENDED,

	PENDING,
	/**
	 * Vendor is permanently blocked or removed from the platform.
	 */
	REJECTED
}
