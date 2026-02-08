import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function PublicRoute({ children }) {
  const { token, userRole, loading } = useAuth();

  // wait for auth hydration
  if (loading) return null;

  // already logged in → redirect
  if (token && userRole) {
    if (userRole === "ADMIN") {
      return <Navigate to="/admin" replace />;
    }

    if (userRole === "VENDOR") {
      return <Navigate to="/vendor/dashboard" replace />;
    }

    return <Navigate to="/user/home" replace />;
  }

  // not logged in → allow access
  return children;
}
