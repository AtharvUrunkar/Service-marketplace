import { Navigate, Link } from "react-router-dom";
import { useAuth } from "./context/AuthContext";

export default function PublicHome() {
  const { token, userRole } = useAuth();

  // 🔑 If already logged in → redirect properly
  if (token) {
    if (userRole === "USER") return <Navigate to="/user/home" replace />;
    if (userRole === "VENDOR") return <Navigate to="/vendor/dashboard" replace />;
    if (userRole === "ADMIN") return <Navigate to="/admin" replace />;
  }

  return (
    <div style={{ padding: 40 }}>
      <h1>Marketplace</h1>
      <p>Please login to continue</p>

      <Link to="/login">
        <button>Login</button>
      </Link>

      <br /><br />

      <Link to="/register">
        <button>Register</button>
      </Link>
    </div>
  );
}
