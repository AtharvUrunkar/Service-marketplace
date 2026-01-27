import axios from "axios";

const API_BASE = "http://localhost:8080/api";

// Apply to become vendor (USER)
export const applyForVendor = (data) => {
  const token = localStorage.getItem("accessToken");

  return axios.post(
    `${API_BASE}/vendor/apply`,
    data,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    }
  );
};


// Get logged-in vendor profile (VENDOR)
export const getMyVendorProfile = () => {
  const token = localStorage.getItem("accessToken");

  return axios.get(`${API_BASE}/vendor/me`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

// Get vendor application status (USER)
export const getVendorStatus = () => {
  const token = localStorage.getItem("accessToken");

  return axios.get(`${API_BASE}/vendor/status`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
