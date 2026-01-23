import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function ProtectedRoute({ role, children }) {
  const { token, userRole, loading } = useAuth();

  // 🔑 Wait until auth is hydrated
  if (loading) {
    return <div>Loading...</div>;
  }

  // Not logged in
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Logged in but wrong role
  if (role && role !== userRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
}
