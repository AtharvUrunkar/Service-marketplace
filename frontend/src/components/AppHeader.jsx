import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function AppHeader() {
  const { userRole, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  if (!userRole) return null; // not logged in

  return (
    <div
      style={{
        padding: "10px 20px",
        borderBottom: "1px solid #ccc",
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <strong>
        {userRole} Dashboard
      </strong>

      <button onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}
