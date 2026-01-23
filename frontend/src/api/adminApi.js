import axios from "axios";

const API_BASE = "http://localhost:8080/api";

const authHeader = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  },
});

// 🔹 Vendor requests
export const getPendingVendors = () => {
  return axios.get(`${API_BASE}/admin/vendors/pending`, authHeader());
};

export const getActiveVendors = () => {
  return axios.get(`${API_BASE}/admin/vendors/active`, authHeader());
};

export const approveVendor = (vendorId) => {
  return axios.post(
    `${API_BASE}/admin/vendors/${vendorId}/approve`,
    {},
    authHeader()
  );
};

export const rejectVendor = (vendorId) => {
  return axios.post(
    `${API_BASE}/admin/vendors/${vendorId}/reject`,
    {},
    authHeader()
  );
};

export const removeVendor = (vendorId) => {
  return axios.delete(
    `${API_BASE}/admin/vendors/${vendorId}`,
    authHeader()
  );
};
