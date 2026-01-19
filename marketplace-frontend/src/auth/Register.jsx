import { useState } from "react";
import { registerApi } from "../api/authApi";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: ""
  });
  const navigate = useNavigate();

const [error, setError] = useState("");

const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    await registerApi(form);
    navigate("/login");
  } catch (err) {
    setError(err.message);
  }
};


  return (
    <form onSubmit={handleSubmit}>
      <h2>Register</h2>
      <input placeholder="Name" onChange={e => setForm({...form, name: e.target.value})} />
      <input placeholder="Email" onChange={e => setForm({...form, email: e.target.value})} />
      <input type="password" placeholder="Password" onChange={e => setForm({...form, password: e.target.value})} />
      <button type="submit">Register</button>
    </form>
  );
}
