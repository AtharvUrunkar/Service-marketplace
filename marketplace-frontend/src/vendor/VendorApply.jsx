import { useState } from "react";
import { applyForVendor } from "../api/vendorApi";

export default function VendorApply() {
  const [form, setForm] = useState({
    name: "",
    gstNumber: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await applyForVendor(form);
      alert("Vendor application submitted. Await admin approval.");
      console.log(res);
    } catch (err) {
      alert(err.response?.data?.message || "Application failed");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Apply as Vendor</h2>

      <input
        name="name"
        placeholder="Business Name"
        onChange={handleChange}
        required
      />

      <input
        name="gstNumber"
        placeholder="GST Number"
        onChange={handleChange}
        required
      />

      <button type="submit">Apply</button>
    </form>
  );
}
