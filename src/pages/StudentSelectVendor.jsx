import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function StudentSelectVendor() {
  const [vendors, setVendors] = useState([])
  const [selectedVendor, setSelectedVendor] = useState("")
  const navigate = useNavigate()

  useEffect(() => {
    api.get("/vendor/all")
      .then(res => setVendors(res.data))
      .catch(() => alert("Failed to load vendors"))
  }, [])

  const handleSelect = () => {
    if (!selectedVendor) {
      alert("Please select a mess vendor")
      return
    }

    api.put(`/student/select-vendor/${selectedVendor}`)
      .then(() => navigate("/student/menu"))
      .catch(() => alert("Selection failed ❌"))
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-extrabold text-gray-800">
          Select Your Mess 🍽️
        </h1>
        <LogoutButton />
      </div>

      <div className="max-w-lg mx-auto bg-white rounded-2xl shadow-lg p-8">
        <p className="text-gray-600 mb-4">
          Choose the mess vendor you want to subscribe to.
        </p>

        <select
          className="w-full border rounded-xl p-3 mb-6 focus:outline-none focus:ring-2 focus:ring-indigo-400"
          value={selectedVendor}
          onChange={e => setSelectedVendor(e.target.value)}
        >
          <option value="">-- Select Mess Vendor --</option>
          {vendors.map(v => (
            <option key={v.vendorId} value={v.vendorId}>
              {v.messName}
            </option>
          ))}
        </select>

        <button
          onClick={handleSelect}
          className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-semibold transition"
        >
          Confirm Mess ➡
        </button>
      </div>
    </div>
  )
}

export default StudentSelectVendor
