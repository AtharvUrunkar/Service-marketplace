import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function ProtectedRoute({ role, children }) {
  const { token, userRole, loading } = useAuth();

  // wait for auth hydration
  if (loading) {
    return <div>Loading...</div>;
  }

  // not logged in
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // role check (SAFE)
  if (role && userRole && role !== userRole) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
}
