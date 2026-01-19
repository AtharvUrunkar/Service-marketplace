import axiosInstance from "../utils/axiosInstance";

// CUSTOMER applies to become vendor
export const applyForVendor = async (data) => {
  const res = await axiosInstance.post("/vendor/apply", data);
  return res.data;
};

// Get current vendor status
export const getMyVendorProfile = async () => {
  const res = await axiosInstance.get("/vendor/me");
  return res.data;
};
