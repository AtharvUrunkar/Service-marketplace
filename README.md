# 🛒 Marketplace Backend (Spring Boot)

A production-grade backend for a **multi-vendor marketplace**, built using **Spring Boot**, **JPA/Hibernate**, **JWT authentication**, and **MySQL**.

This project focuses on **real-world backend concerns** such as domain modeling, approval workflows, role-based access control, data integrity, and clean API design.

---

## 🚀 Features

### 👤 User & Vendor Management
- Users can register and authenticate using JWT.
- Customers can **apply to become vendors**.
- Admins **approve or reject vendor applications**.
- Vendor role is granted **only after admin approval**.

### 🏪 Vendor Onboarding Flow

CUSTOMER
→ apply for vendor
→ PENDING_APPROVAL
→ admin approval
→ VENDOR (APPROVED)


### 📦 Product Lifecycle
- Vendors create products as **DRAFT**.
- Vendors explicitly **submit products for approval**.
- Admins approve products before they become active.


### 🔐 Security & Authorization
- JWT-based authentication.
- Clear role separation:
  - CUSTOMER
  - VENDOR
  - ADMIN
- Domain-level validation:
  - Role checks
  - Vendor ownership checks
  - Lifecycle enforcement

### 🧱 Clean Architecture
- Controllers return **DTOs**, never JPA entities.
- Business rules enforced in **service layer**.
- Strong database constraints (foreign keys, enums as strings).
- No lazy-loading serialization issues.

---

## 🧩 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security (JWT)**
- **Hibernate / JPA**
- **MySQL**
- **Lombok**
- **Maven**

---

## 🗂️ Project Structure
src
└── main
    └── java
        └── com
            └── marketplace
                ├── auth
                ├── cart
                ├── product
                │   ├── controller
                │   ├── service
                │   ├── entity
                │   ├── dto
                │   └── enums
                ├── vendor
                │   ├── controller
                │   ├── service
                │   ├── entity
                │   ├── dto
                │   └── enums
                ├── order
                ├── security
                └── entity

---

## 🔁 Core API Flows

### 🔹 Vendor Apply

POST /vendor/apply
**Auth:** CUSTOMER  
**Result:** Vendor created with `PENDING_APPROVAL`

---

### 🔹 Vendor Approval (Admin)

PUT /admin/vendors/{vendorId}/approve

**Auth:** ADMIN  
**Result:** Vendor approved + user role upgraded to `VENDOR`

---

### 🔹 Product Creation (Vendor)
POST /products

**Auth:** VENDOR  
**Result:** Product created as `DRAFT`

---

### 🔹 Submit Product for Approval
PUT /products/{productId}/submit

**Auth:** VENDOR  
**Result:** Product moves to `PENDING_APPROVAL`

---

### 🔹 Product Approval (Admin)

**Auth:** VENDOR  
**Result:** Product moves to `PENDING_APPROVAL`

---

### 🔹 Product Approval (Admin)

**Auth:** ADMIN  
**Result:** Product becomes `APPROVED`

---

## 🧠 Design Decisions (Important)

- **Roles ≠ Domain Entities**
  - Vendor is a domain entity, not just a role.
- **Approval-based workflows**
  - Prevents unauthorized access and fake vendors.
- **EnumType.STRING**
  - Avoids enum-ordinal bugs and schema lock-in.
- **No entity exposure**
  - Prevents Hibernate lazy loading issues.
- **Database constraints first**
  - Foreign keys and strict mode catch bugs early.

---

## 🧪 Testing Strategy

- Manual testing via Postman (current).
- Service-level validation for:
  - Role checks
  - Ownership checks
  - State transitions
- Planned:
  - Unit tests for services
  - Integration tests for critical flows

---

## ⚠️ Known Limitations / Future Work

- Add global exception handling (`@ControllerAdvice`)
- Add reject flows (vendor/product rejection with reason)
- Implement checkout → order → payment flow
- Add automated tests
- Improve observability (logging, tracing)

---

## 📌 How to Run Locally

1. Clone the repository
2. Configure `application.yml` with MySQL credentials
3. Run:
   ```bash
   mvn spring-boot:run
