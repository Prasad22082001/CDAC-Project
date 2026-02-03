import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function AdminVendors() {
  const [vendors, setVendors] = useState([])
  const [loading, setLoading] = useState(true)

  // 🔹 ADD VENDOR FORM STATE
  const [messName, setMessName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [contact, setContact] = useState("")

  // 🔥 VIVA SAFE
  const adminId = 1

  // ================= FETCH VENDORS =================
  const fetchVendors = () => {
    setLoading(true)
    api
      .get("/vendor/all")
      .then((res) => {
        setVendors(res.data)
        setLoading(false)
      })
      .catch(() => setLoading(false))
  }

  useEffect(() => {
    fetchVendors()
  }, [])

  // ================= ADD VENDOR =================
  const handleAddVendor = () => {
    if (!messName || !email || !password || !contact) {
      alert("All fields are required")
      return
    }

    api
      .post("/vendor/add", {
        messName,
        email,
        password,
        contact,
        adminId,
      })
      .then(() => {
        alert("Vendor added successfully ✅")
        setMessName("")
        setEmail("")
        setPassword("")
        setContact("")
        fetchVendors()
      })
      .catch(() => alert("Add vendor failed ❌"))
  }

  // ================= DELETE VENDOR =================
  const handleDelete = (id) => {
    if (!window.confirm("Delete this vendor?")) return

    api
      .delete(`/vendor/${id}`)
      .then(() => {
        alert("Vendor deleted 🗑️")
        fetchVendors()
      })
      .catch(() => alert("Delete failed ❌"))
  }

  if (loading)
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 flex items-center justify-center p-8">
        <div className="bg-white rounded-2xl shadow-lg p-8 text-center max-w-md mx-auto">
          <div className="text-4xl mb-4 animate-pulse">🍽️</div>
          <p className="text-xl text-gray-600 font-semibold">Loading vendors...</p>
        </div>
      </div>
    )

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Manage Vendors
          </h1>
          <p className="text-gray-500 mt-1">
            Add, view and manage mess vendors
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* ================= ADD VENDOR FORM ================= */}
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-12 max-w-6xl mx-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 flex items-center">
          🍽️ Add New Vendor
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Mess Name</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition"
              placeholder="Enter mess name"
              value={messName}
              onChange={(e) => setMessName(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Email</label>
            <input
              type="email"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition"
              placeholder="vendor@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Contact</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition"
              placeholder="Phone number"
              value={contact}
              onChange={(e) => setContact(e.target.value)}
            />
          </div>
          <div className="lg:col-span-2">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Password</label>
            <input
              type="password"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition"
              placeholder="Enter secure password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div className="lg:col-span-3 flex items-end">
            <button
              onClick={handleAddVendor}
              className="w-full bg-gradient-to-r from-green-600 to-green-700 hover:from-green-700 hover:to-green-800 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-green-500 text-lg flex items-center justify-center"
            >
              ➕ Add Vendor
            </button>
          </div>
        </div>
      </div>

      {/* ================= VENDOR LIST ================= */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-6xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            📋 Vendor List
            <span className="ml-2 bg-green-100 text-green-800 text-sm font-semibold px-3 py-1 rounded-full">
              {vendors.length} vendor(s)
            </span>
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Mess Name</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Email</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Contact</th>
                <th className="px-8 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {vendors.length === 0 ? (
                <tr>
                  <td colSpan="5" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">🍽️</div>
                      <p className="text-lg">No vendors found</p>
                      <p className="text-sm text-gray-400 mt-1">Add your first vendor above</p>
                    </div>
                  </td>
                </tr>
              ) : (
                vendors.map((v) => (
                  <tr
                    key={v.vendorId}
                    className="hover:bg-gray-50 transition-colors"
                  >
                    <td className="px-8 py-6 font-mono text-sm text-gray-900 bg-gray-50">{v.vendorId}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">{v.messName}</td>
                    <td className="px-8 py-6 text-sm text-gray-700 max-w-md truncate">{v.email}</td>
                    <td className="px-8 py-6 text-sm text-gray-700">{v.contact}</td>
                    <td className="px-8 py-6 text-center">
                      <button
                        onClick={() => handleDelete(v.vendorId)}
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

export default AdminVendors
