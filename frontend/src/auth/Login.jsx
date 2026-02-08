import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { loginApi } from "../api/authApi";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const { login, token, userRole } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (loading) return;

    setLoading(true);
    setError("");

    try {
      const res = await loginApi(email, password);
      login(res.token, res.role); // ONLY store auth
    } catch (err) {
      setError(err.message || "Invalid credentials");
      setLoading(false);
    }
  };

  // 🔥 ROLE-BASED REDIRECT (CORRECT WAY)
  useEffect(() => {
    if (!token || !userRole) return;

    if (userRole === "ADMIN") {
      navigate("/admin", { replace: true });
    } else if (userRole === "VENDOR") {
      navigate("/vendor/dashboard", { replace: true });
    } else {
      navigate("/user/home", { replace: true });
    }
  }, [token, userRole, navigate]);

  return (
    <div style={{ padding: 30, maxWidth: 400 }}>
      <h2>Login</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <br /><br />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          autoComplete="current-password"
          required
        />

        <br /><br />

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>

      <br />

      <p>
        Don’t have an account? <Link to="/register">Register</Link>
      </p>
    </div>
  );
}
