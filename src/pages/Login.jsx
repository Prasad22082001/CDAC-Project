import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import api from "../services/api"

function Login() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [role, setRole] = useState("ADMIN")
  const [error, setError] = useState("")
  const navigate = useNavigate()

  // 🔥 clear old token
  useEffect(() => {
    localStorage.removeItem("token")
  }, [])

  const handleLogin = async () => {
    setError("")

    try {
      let url = ""
      if (role === "ADMIN") url = "/admin/login"
      else if (role === "VENDOR") url = "/vendor/login"
      else url = "/student/login"

      const res = await api.post(url, { email, password })

      localStorage.setItem("token", res.data.jwt)

      if (role === "ADMIN") navigate("/admin")
      else if (role === "VENDOR") navigate("/vendor")
      else navigate("/student/select-vendor")
    } catch {
      setError("Invalid credentials ❌")
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center
      bg-gradient-to-br from-blue-50 via-white to-indigo-50">

      <div className="bg-white p-8 rounded-2xl shadow-xl w-96 border">

        {/* TITLE */}
        <h2 className="text-3xl font-extrabold text-center text-gray-800 mb-1">
          Smart Mess
        </h2>
        <p className="text-gray-500 text-center mb-6">
          Management System Login
        </p>

        {/* ERROR */}
        {error && (
          <p className="text-red-500 text-center mb-3 font-medium">
            {error}
          </p>
        )}

        {/* ROLE */}
        <label className="text-sm font-medium text-gray-600">
          Login As
        </label>
        <select
          className="w-full mt-1 mb-4 border rounded-lg p-2
          focus:ring-2 focus:ring-blue-500 outline-none"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          <option value="ADMIN">Admin</option>
          <option value="VENDOR">Vendor</option>
          <option value="STUDENT">Student</option>
        </select>

        {/* EMAIL */}
        <label className="text-sm font-medium text-gray-600">
          Email
        </label>
        <input
          className="w-full mt-1 mb-4 border rounded-lg p-2
          focus:ring-2 focus:ring-blue-500 outline-none"
          placeholder="Enter email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        {/* PASSWORD */}
        <label className="text-sm font-medium text-gray-600">
          Password
        </label>
        <input
          type="password"
          className="w-full mt-1 mb-6 border rounded-lg p-2
          focus:ring-2 focus:ring-blue-500 outline-none"
          placeholder="Enter password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        {/* BUTTON */}
        <button
          onClick={handleLogin}
          className="w-full bg-blue-600 hover:bg-blue-700
          transition text-white py-2 rounded-xl font-semibold shadow"
        >
          Login
        </button>

        {/* FOOTER */}
        <p className="text-xs text-gray-400 text-center mt-6">
          © Smart Mess Management System
        </p>
      </div>
    </div>
  )
}

export default Login
