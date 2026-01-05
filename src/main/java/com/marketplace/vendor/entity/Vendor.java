package com.marketplace.vendor.entity;

import com.marketplace.entity.User;
import com.marketplace.vendor.enums.VendorStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	// 🔥 THIS FIELD WAS MISSING OR WRONG
	@OneToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private VendorStatus status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false, unique = true)
	private String gstNumber;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.status = VendorStatus.PENDING;
	}
}
