import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./auth/Login";
import Register from "./auth/Register";
import ProtectedRoute from "./components/ProtectedRoute";
import { AuthProvider } from "./context/AuthContext";

import UserHome from "./user/UserHome";
import VendorDashboard from "./vendor/VendorDashboard";
import AdminDashboard from "./admin/AdminDashboard";
import PublicHome from "./PublicHome";
import Unauthorized from "./Unauthorized";
import PublicRoute from "./components/PublicRoute";


export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Public */}
            <Route
  path="/"
  element={
    <PublicRoute>
      <PublicHome />
    </PublicRoute>
  }
/>

           <Route
  path="/login"
  element={
    <PublicRoute>
      <Login />
    </PublicRoute>
  }
/>

<Route
  path="/register"
  element={
    <PublicRoute>
      <Register />
    </PublicRoute>
  }
/>

            <Route path="/unauthorized" element={<Unauthorized />}
             />
          {/* User */}
          <Route
            path="/user/home"
            element={
              <ProtectedRoute role="CUSTOMER">
                <UserHome />
              </ProtectedRoute>
            }
          />

          {/* Vendor */}
          <Route
            path="/vendor/dashboard"
            element={
              <ProtectedRoute role="VENDOR">
                <VendorDashboard />
              </ProtectedRoute>
            }
          />

          {/* Admin */}
          <Route
            path="/admin"
            element={
              <ProtectedRoute role="ADMIN">
                <AdminDashboard />
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
