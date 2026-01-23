import { useEffect, useState } from "react";
import {
  getPendingVendors,
  getActiveVendors,
  approveVendor,
  rejectVendor,
  removeVendor,
} from "../api/adminApi";

export default function AdminDashboard() {
  const [pendingVendors, setPendingVendors] = useState([]);
  const [activeVendors, setActiveVendors] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadData = async () => {
    try {
      const [pendingRes, activeRes] = await Promise.all([
        getPendingVendors(),
        getActiveVendors(),
      ]);

      setPendingVendors(pendingRes.data);
      setActiveVendors(activeRes.data);
    } catch (err) {
      console.error("Failed to load admin data", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleApprove = async (vendorId) => {
    await approveVendor(vendorId);
    loadData();
  };

  const handleReject = async (vendorId) => {
    await rejectVendor(vendorId);
    loadData();
  };

  const handleRemove = async (vendorId) => {
    await removeVendor(vendorId);
    loadData();
  };

  if (loading) return <h3>Loading admin dashboard...</h3>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Admin Dashboard</h1>

      {/* PENDING VENDORS */}
      <section>
        <h2>Pending Vendor Requests</h2>
        {pendingVendors.length === 0 && <p>No pending requests</p>}

        {pendingVendors.map((vendor) => (
          <div key={vendor.id} style={boxStyle}>
            <p><b>Name:</b> {vendor.businessName}</p>
            <p><b>User ID:</b> {vendor.userId}</p>

            <button onClick={() => handleApprove(vendor.id)}>
              Approve
            </button>
            <button onClick={() => handleReject(vendor.id)}>
              Reject
            </button>
          </div>
        ))}
      </section>

      <hr />

      {/* ACTIVE VENDORS */}
      <section>
        <h2>Active Vendors</h2>
        {activeVendors.length === 0 && <p>No active vendors</p>}

        {activeVendors.map((vendor) => (
          <div key={vendor.id} style={boxStyle}>
            <p><b>Name:</b> {vendor.businessName}</p>
            <p><b>Vendor ID:</b> {vendor.id}</p>

            <button onClick={() => handleRemove(vendor.id)}>
              Remove Vendor
            </button>
          </div>
        ))}
      </section>
    </div>
  );
}

const boxStyle = {
  border: "1px solid #ccc",
  padding: "12px",
  marginBottom: "10px",
  borderRadius: "6px",
};
