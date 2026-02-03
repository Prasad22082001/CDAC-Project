import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function StudentPlan() {
  const [plans, setPlans] = useState([])
  const [selectedPlan, setSelectedPlan] = useState("")
  const navigate = useNavigate()

  useEffect(() => {
    api.get("/plan/all")
      .then(res => setPlans(res.data))
      .catch(() => alert("Failed to load plans"))
  }, [])

  const handleSelect = () => {
    if (!selectedPlan) {
      alert("Please select plan")
      return
    }

    api.put(`/student/select-plan/${selectedPlan}`)
      .then(() => navigate("/student/payment"))
      .catch(() => alert("Plan selection failed"))
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-100 p-8">
      <div className="flex justify-between items-center mb-10">
        <h1 className="text-3xl font-extrabold text-gray-800">
          Choose Mess Plan 📋
        </h1>
        <LogoutButton />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-4xl mx-auto">
        {plans.map(p => (
          <div
            key={p.planId}
            onClick={() => setSelectedPlan(p.planId)}
            className={`cursor-pointer rounded-2xl p-6 shadow-lg transition
              ${
                selectedPlan === p.planId
                  ? "border-2 border-green-600 bg-green-50"
                  : "bg-white hover:scale-[1.02]"
              }`}
          >
            <h2 className="text-xl font-bold text-gray-800">
              {p.planName}
            </h2>
            <p className="text-gray-600 mt-1">
              Duration: {p.durationDays} days
            </p>
            <p className="text-3xl font-extrabold text-green-700 mt-4">
              ₹ {p.price}
            </p>
          </div>
        ))}
      </div>

      <div className="text-center mt-10">
        <button
          onClick={handleSelect}
          className="bg-green-600 hover:bg-green-700 text-white px-10 py-3 rounded-xl font-semibold transition"
        >
          Confirm Plan ➡ Pay
        </button>
      </div>
    </div>
  )
}

export default StudentPlan
