package com.marketplace.vendor.repository;

import com.marketplace.entity.User;
import com.marketplace.vendor.entity.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	// Check if vendor already exists (case-insensitive)
	boolean existsByNameIgnoreCase(String name);

	// Check if a vendor exists for a given user
	boolean existsByUser(User user);

	// Fetch vendor by name
	Optional<Vendor> findByNameIgnoreCase(String name);

	// Fetch vendor by user
	Optional<Vendor> findByUser(User user);
}
