import { useEffect, useState } from "react";
import { getMyVendorProfile } from "../api/vendorApi";
import { useNavigate } from "react-router-dom";

export default function VendorStatus() {
  const [vendor, setVendor] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    getMyVendorProfile()
      .then((res) => {
        setVendor(res);

        if (res.status === "APPROVED") {
          navigate("/vendor/dashboard");
        }
      })
      .catch(() => {
        setVendor(null);
      });
  }, []);

  if (!vendor) return <p>No vendor application found</p>;

  return (
    <div>
      <h2>Vendor Status</h2>
      <p>Status: {vendor.status}</p>

      {vendor.status === "PENDING" && (
        <p>Please wait for admin approval</p>
      )}
    </div>
  );
}
