import { useEffect, useState } from "react";
import { getMyVendorProfile } from "../api/vendorApi";

function VendorStatus() {
  const [status, setStatus] = useState("LOADING");

  useEffect(() => {
    getMyVendorProfile()
      .then((data) => setStatus(data.status))
      .catch(() => setStatus("NOT_APPLIED"));
  }, []);

  if (status === "LOADING") return <p>Loading...</p>;

  if (status === "PENDING") {
    return <h2>Your vendor application is pending approval ⏳</h2>;
  }

  if (status === "APPROVED") {
    return <h2>Your vendor application is approved ✅</h2>;
  }

  return <h2>You have not applied to become a vendor</h2>;
}

export default VendorStatus;
