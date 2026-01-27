import { useState } from "react";
import { applyForVendor } from "../api/vendorApi";

export default function VendorApplyBox() {
  const [businessName, setBusinessName] = useState("");
  const [gstNumber, setGstNumber] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const applyVendorHandler = async () => {
    setLoading(true);
    setMessage("");

    try {
      await applyForVendor({
        businessName,
        gstNumber,
      });
      setMessage("✅ Application submitted. Await admin approval.");
    } catch (err) {
      console.error(err);
      setMessage("❌ Failed to apply. Check details.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ border: "1px solid #ccc", padding: 16, marginTop: 20 }}>
      <h3>Become a Vendor</h3>

      <input
        placeholder="Business Name"
        value={businessName}
        onChange={(e) => setBusinessName(e.target.value)}
      />

      <br /><br />

      <input
        placeholder="GST Number"
        value={gstNumber}
        onChange={(e) => setGstNumber(e.target.value)}
      />

      <br /><br />

      <button onClick={applyVendorHandler} disabled={loading}>
        {loading ? "Applying..." : "Apply as Vendor"}
      </button>

      {message && <p>{message}</p>}
    </div>
  );
}
