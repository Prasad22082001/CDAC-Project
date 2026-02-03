import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function VendorWorkers() {
  const [workers, setWorkers] = useState([])
  const [name, setName] = useState("")
  const [role, setRole] = useState("cook")
  const [contact, setContact] = useState("")

  const fetchWorkers = () => {
    api.get("/worker/all")
      .then(res => setWorkers(res.data))
      .catch(() => alert("Failed to load workers"))
  }

  useEffect(() => {
    fetchWorkers()
  }, [])

  const handleAddWorker = () => {
    if (!name || !contact) {
      alert("All fields required")
      return
    }

    api.post("/worker/add", { name, role, contact })
      .then(() => {
        setName("")
        setContact("")
        setRole("cook")
        fetchWorkers()
      })
      .catch(() => alert("Add worker failed"))
  }

  const handleDelete = (id) => {
    api.delete(`/worker/delete/${id}`)
      .then(fetchWorkers)
      .catch(() => alert("Delete failed"))
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Manage Workers
          </h1>
          <p className="text-gray-500 mt-1">
            Add, view and manage your mess workers
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* ADD WORKER FORM */}
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-12 max-w-4xl mx-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 flex items-center">
          👷 Add New Worker
        </h2>
        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
          <div className="lg:col-span-1">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Worker Name</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
              placeholder="Enter worker name"
              value={name}
              onChange={e => setName(e.target.value)}
            />
          </div>
          <div className="lg:col-span-1">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Role</label>
            <select
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition bg-white"
              value={role}
              onChange={e => setRole(e.target.value)}
            >
              <option value="cook">Cook</option>
              <option value="cleaner">Cleaner</option>
              <option value="helper">Helper</option>
            </select>
          </div>
          <div className="lg:col-span-1">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Contact</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
              placeholder="Phone number"
              value={contact}
              onChange={e => setContact(e.target.value)}
            />
          </div>
          <div className="lg:col-span-1 flex items-end">
            <button
              onClick={handleAddWorker}
              className="w-full h-[58px] bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white font-semibold px-6 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-blue-500 text-lg flex items-center justify-center"
              disabled={!name || !contact}
            >
              ➕ Add Worker
            </button>
          </div>
        </div>
      </div>

      {/* WORKER LIST */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-4xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            📋 Worker List
            <span className="ml-2 bg-blue-100 text-blue-800 text-sm font-semibold px-3 py-1 rounded-full">
              {workers.length} worker(s)
            </span>
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Name</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Role</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Contact</th>
                <th className="px-8 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {workers.length === 0 ? (
                <tr>
                  <td colSpan="5" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">👷</div>
                      <p className="text-lg">No workers found</p>
                      <p className="text-sm text-gray-400 mt-1">Add your first worker above</p>
                    </div>
                  </td>
                </tr>
              ) : (
                workers.map(w => (
                  <tr
                    key={w.workerId}
                    className="hover:bg-gray-50 transition-colors"
                  >
                    <td className="px-8 py-6 font-mono text-sm text-gray-900 bg-gray-50">{w.workerId}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">{w.name}</td>
                    <td className="px-8 py-6 text-sm text-gray-700 capitalize">{w.role}</td>
                    <td className="px-8 py-6 text-sm text-gray-700">{w.contact}</td>
                    <td className="px-8 py-6 text-center">
                      <button
                        onClick={() => handleDelete(w.workerId)}
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
      <div className="mt-12 text-center max-w-4xl mx-auto">
        <Link
          to="/vendor/dashboard"
          className="inline-flex items-center text-blue-600 hover:text-blue-800 font-semibold transition"
        >
          ← Back to Dashboard
        </Link>
      </div>
    </div>
  )
}

export default VendorWorkers
