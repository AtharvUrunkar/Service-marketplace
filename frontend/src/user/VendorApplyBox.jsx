import { useState } from "react";
import { applyForVendor } from "../api/vendorApi";

export default function VendorApplyBox() {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const applyVendorHandler = async () => {
    setLoading(true);
    try {
      await applyVendor();
      setMessage("✅ Application submitted. Await admin approval.");
    } catch (err) {
      setMessage("❌ Failed to apply. Try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        border: "1px solid #ccc",
        padding: "16px",
        marginTop: "20px",
        borderRadius: "8px",
        maxWidth: "400px",
      }}
    >
      <h3>Become a Vendor</h3>
      <p>Apply to sell products or services on the platform.</p>

      <button onClick={applyVendorHandler} disabled={loading}>
        {loading ? "Applying..." : "Apply as Vendor"}
      </button>

      {message && <p>{message}</p>}
    </div>
  );
}
