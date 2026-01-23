import { useEffect, useState } from "react";
import { getVendorStatus } from "../api/vendorApi";
import VendorApplyBox from "./VendorApplyBox";

export default function UserHome() {
  const [vendorStatus, setVendorStatus] = useState("NONE");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let mounted = true;

    const fetchStatus = async () => {
      try {
        const res = await getVendorStatus();
        if (mounted && res?.data?.status) {
          setVendorStatus(res.data.status);
        }
      } catch (err) {
        // VERY IMPORTANT: never crash
        console.warn("Vendor status fetch failed, defaulting to NONE");
        setVendorStatus("NONE");
      } finally {
        if (mounted) setLoading(false);
      }
    };

    fetchStatus();

    return () => {
      mounted = false;
    };
  }, []);

  if (loading) return <h3>Loading user dashboard...</h3>;

  return (
    <div style={{ padding: 20 }}>
      <h1>User Dashboard</h1>

      {vendorStatus === "NONE" && <VendorApplyBox />}
      {vendorStatus === "PENDING" && (
        <p>🕒 Your vendor application is under review</p>
      )}
      {vendorStatus === "APPROVED" && (
        <p>✅ You are an approved vendor. Use Vendor Login.</p>
      )}
    </div>
  );
}
