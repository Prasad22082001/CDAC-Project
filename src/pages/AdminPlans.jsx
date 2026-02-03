import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function AdminPlans() {
  const [plans, setPlans] = useState([])
  const [loading, setLoading] = useState(false)

  const [planName, setPlanName] = useState("")
  const [price, setPrice] = useState("")
  const [durationDays, setDurationDays] = useState("")

  const fetchPlans = () => {
    setLoading(true)
    api
      .get("/plan/all")
      .then((res) => {
        setPlans(res.data)
        setLoading(false)
      })
      .catch(() => setLoading(false))
  }

  useEffect(() => {
    fetchPlans()
  }, [])

  const handleAddPlan = () => {
    if (!planName || !price || !durationDays) {
      alert("All fields required")
      return
    }

    api
      .post("/plan/add", {
        planName,
        price: Number(price),
        durationDays: Number(durationDays),
        adminId: 1, // 🔥 VIVA SAFE
      })
      .then(() => {
        alert("Plan added ✅")
        setPlanName("")
        setPrice("")
        setDurationDays("")
        fetchPlans()
      })
      .catch(() => alert("Add failed ❌"))
  }

  const handleDeletePlan = (id) => {
    if (!window.confirm("Delete this plan?")) return

    api
      .delete(`/plan/delete/${id}`)
      .then(() => {
        alert("Deleted 🗑️")
        fetchPlans()
      })
      .catch(() => alert("Delete failed ❌"))
  }

  if (loading)
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 flex items-center justify-center p-8">
        <div className="bg-white rounded-2xl shadow-lg p-8 text-center max-w-md mx-auto">
          <div className="text-4xl mb-4 animate-pulse">📋</div>
          <p className="text-xl text-gray-600 font-semibold">Loading plans...</p>
        </div>
      </div>
    )

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Manage Plans
          </h1>
          <p className="text-gray-500 mt-1">
            Create and manage mess plans
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* ================= ADD PLAN FORM ================= */}
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-12 max-w-4xl mx-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 flex items-center">
          📋 Add New Mess Plan
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Plan Name</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
              placeholder="Enter plan name"
              value={planName}
              onChange={(e) => setPlanName(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Price (₹)</label>
            <input
              type="number"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
              placeholder="0"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </div>
          <div className="md:col-span-1 flex items-end">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Duration (days)</label>
            <input
              type="number"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
              placeholder="30"
              value={durationDays}
              onChange={(e) => setDurationDays(e.target.value)}
            />
          </div>
        </div>
        <div className="mt-8 flex justify-center md:justify-start">
          <button
            onClick={handleAddPlan}
            className="bg-gradient-to-r from-purple-600 to-purple-700 hover:from-purple-700 hover:to-purple-800 text-white font-semibold py-4 px-8 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-purple-500 text-lg flex items-center"
          >
            ➕ Add Plan
          </button>
        </div>
      </div>

      {/* ================= PLAN LIST ================= */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-4xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            📋 Plan List
            <span className="ml-2 bg-purple-100 text-purple-800 text-sm font-semibold px-3 py-1 rounded-full">
              {plans.length} plan(s)
            </span>
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Plan Name</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Price</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Duration</th>
                <th className="px-8 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {plans.length === 0 ? (
                <tr>
                  <td colSpan="5" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">📋</div>
                      <p className="text-lg">No plans found</p>
                      <p className="text-sm text-gray-400 mt-1">Add your first plan above</p>
                    </div>
                  </td>
                </tr>
              ) : (
                plans.map((plan) => (
                  <tr
                    key={plan.planId}
                    className="hover:bg-gray-50 transition-colors"
                  >
                    <td className="px-8 py-6 font-mono text-sm text-gray-900 bg-gray-50">{plan.planId}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">{plan.planName}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">₹{plan.price}</td>
                    <td className="px-8 py-6 text-sm text-gray-700">{plan.durationDays} days</td>
                    <td className="px-8 py-6 text-center">
                      <button
                        onClick={() => handleDeletePlan(plan.planId)}
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
          to="/admin/dashboard"
          className="inline-flex items-center text-blue-600 hover:text-blue-800 font-semibold transition"
        >
          ← Back to Dashboard
        </Link>
      </div>
    </div>
  )
}

export default AdminPlans
