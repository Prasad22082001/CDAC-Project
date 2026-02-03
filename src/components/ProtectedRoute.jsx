import { Navigate } from "react-router-dom"
import { jwtDecode } from "jwt-decode"

function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token")

  if (!token) return <Navigate to="/" replace />

  try {
    jwtDecode(token)
    return children
  } catch {
    localStorage.removeItem("token")
    return <Navigate to="/" replace />
  }
}

export default ProtectedRoute
