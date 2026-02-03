import { useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function AdminAdd() {
  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [contact, setContact] = useState("")
  const [password, setPassword] = useState("")
  const [msg, setMsg] = useState("")

  const handleAddAdmin = () => {
    api.post("/admin/add", {
      name,
      email,
      contact,
      password
    })
    .then(() => {
      setMsg("✅ Admin added successfully")
      setName("")
      setEmail("")
      setContact("")
      setPassword("")
    })
    .catch(() => {
      setMsg("❌ Failed to add admin")
    })
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Add Admin
          </h1>
          <p className="text-gray-500 mt-1">
            Create new admin account
          </p>
        </div>
        <LogoutButton />
      </div>

      <div className="max-w-md mx-auto">
        <div className="bg-white rounded-2xl shadow-lg p-8">
          <div className="space-y-6">
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                👤 Name
              </label>
              <input
                className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                placeholder="Enter admin name"
                value={name}
                onChange={e => setName(e.target.value)}
              />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                📧 Email
              </label>
              <input
                type="email"
                className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                placeholder="admin@example.com"
                value={email}
                onChange={e => setEmail(e.target.value)}
              />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                📱 Contact
              </label>
              <input
                className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                placeholder="Enter phone number"
                value={contact}
                onChange={e => setContact(e.target.value)}
              />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">
                🔒 Password
              </label>
              <input
                type="password"
                className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
                placeholder="Enter secure password"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
            </div>

            <button
              onClick={handleAddAdmin}
              className="w-full bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-blue-500 text-lg"
            >
              ➕ Add Admin
            </button>

            {msg && (
              <div className={`p-4 rounded-xl text-center font-semibold ${msg.includes('✅') ? 'bg-green-100 text-green-800 border-2 border-green-200' : 'bg-red-100 text-red-800 border-2 border-red-200'}`}>
                {msg}
              </div>
            )}
          </div>
        </div>

        {/* BACK LINK */}
        <div className="mt-8 text-center">
          <Link
            to="/admin/admins"
            className="inline-flex items-center text-blue-600 hover:text-blue-800 font-semibold transition"
          >
            ← Back to Admins
          </Link>
        </div>
      </div>
    </div>
  )
}

export default AdminAdd
