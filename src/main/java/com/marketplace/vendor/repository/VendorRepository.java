package com.marketplace.vendor.repository;

import com.marketplace.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	// Check if vendor already exists (case-insensitive)
	boolean existsByNameIgnoreCase(String name);

	// Optional helper for fetching by name
	Optional<Vendor> findByNameIgnoreCase(String name);
}
