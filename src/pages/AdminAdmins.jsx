import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function AdminAdmins() {
  const [admins, setAdmins] = useState([])
  const [form, setForm] = useState({
    name: "",
    email: "",
    contact: "",
    password: "",
  })

  const loadAdmins = () => {
    api.get("/admin/all")
      .then(res => setAdmins(res.data))
      .catch(() => setAdmins([]))
  }

  useEffect(() => {
    loadAdmins()
  }, [])

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const addAdmin = () => {
    api.post("/admin/add", form)
      .then(() => {
        setForm({ name: "", email: "", contact: "", password: "" })
        loadAdmins()
      })
      .catch(() => alert("Add admin failed"))
  }

  const deleteAdmin = id => {
    if (!window.confirm("Delete this admin?")) return

    api.delete(`/admin/delete/${id}`)
      .then(loadAdmins)
      .catch(() => alert("Delete failed"))
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Manage Admins
          </h1>
          <p className="text-gray-500 mt-1">
            Add, view and manage admin accounts
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* ADD ADMIN FORM */}
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-12 max-w-6xl mx-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 flex items-center">
          👑 Add New Admin
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Name</label>
            <input
              name="name"
              value={form.name}
              onChange={handleChange}
              placeholder="Enter admin name"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Email</label>
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={handleChange}
              placeholder="admin@example.com"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Contact</label>
            <input
              name="contact"
              value={form.contact}
              onChange={handleChange}
              placeholder="Phone number"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
            />
          </div>
          <div className="lg:col-span-2">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Password</label>
            <input
              name="password"
              type="password"
              value={form.password}
              onChange={handleChange}
              placeholder="Enter secure password"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
            />
          </div>
          <div className="lg:col-span-3 flex items-end">
            <button
              onClick={addAdmin}
              className="w-full bg-gradient-to-r from-green-600 to-green-700 hover:from-green-700 hover:to-green-800 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-green-500 text-lg flex items-center justify-center"
            >
              ➕ Add Admin
            </button>
          </div>
        </div>
      </div>

      {/* ADMIN TABLE */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-6xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            📋 Admin List
            <span className="ml-2 bg-blue-100 text-blue-800 text-sm font-semibold px-3 py-1 rounded-full">
              {admins.length} admin(s)
            </span>
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Name</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Email</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Contact</th>
                <th className="px-8 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {admins.length === 0 ? (
                <tr>
                  <td colSpan="5" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">👑</div>
                      <p className="text-lg">No admins found</p>
                      <p className="text-sm text-gray-400 mt-1">Add your first admin above</p>
                    </div>
                  </td>
                </tr>
              ) : (
                admins.map(a => (
                  <tr
                    key={a.adminId}
                    className="hover:bg-gray-50 transition-colors"
                  >
                    <td className="px-8 py-6 font-mono text-sm text-gray-900 bg-gray-50">{a.adminId}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">{a.name}</td>
                    <td className="px-8 py-6 text-sm text-gray-700 max-w-md truncate">{a.email}</td>
                    <td className="px-8 py-6 text-sm text-gray-700">{a.contact}</td>
                    <td className="px-8 py-6 text-center">
                      <button
                        onClick={() => deleteAdmin(a.adminId)}
                        className="bg-gradient-to-r from-red-600 to-red-700 hover:from-red-700 hover:to-red-800 text-white px-6 py-2 rounded-xl font-semibold shadow-lg hover:scale-[1.02] transition transform border-l-4 border-red-500 text-sm"
                      >
                        🗑️ Delete
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* BACK LINK */}
      <div className="mt-12 text-center max-w-6xl mx-auto">
        <Link
          to="/admin/dashboard"
          className="inline-flex items-center text-blue-600 hover:text-blue-800 font-semibold transition"
        >
          ← Back to Dashboard
        </Link>
      </div>
    </div>
  )
}

export default AdminAdmins
