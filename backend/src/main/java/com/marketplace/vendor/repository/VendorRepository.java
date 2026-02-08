package com.marketplace.vendor.repository;

import com.marketplace.entity.User;
import com.marketplace.vendor.entity.Vendor;

import com.marketplace.vendor.enums.VendorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface VendorRepository extends JpaRepository<Vendor, Long> {

	boolean existsByNameIgnoreCase(String name);

	boolean existsByUser(User user);

	Optional<Vendor> findByNameIgnoreCase(String name);

	Optional<Vendor> findByUser(User user);

	// 🔥 ADMIN USE CASES
	List<Vendor> findByStatus(VendorStatus status);
}
