import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function StudentPayment() {
  const [plan, setPlan] = useState(null)
  const [mode, setMode] = useState("UPI")
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  useEffect(() => {
    api.get("/student/me")
      .then(res => {
        const planId = res.data.planId
        if (!planId) {
          alert("Please select plan first")
          navigate("/student/plan")
          return
        }

        api.get(`/plan/${planId}`)
          .then(p => setPlan(p.data))
      })
      .catch(() => alert("Failed to load plan"))
  }, [])

  const handlePayment = () => {
    setLoading(true)

    api.post("/payment/pay", {
      amount: plan.price,
      paymentMode: mode
    })
      .then(() => navigate("/student/payment-history"))
      .catch(() => alert("Payment failed ❌"))
      .finally(() => setLoading(false))
  }

  if (!plan) return <p className="p-6">Loading...</p>

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 to-purple-100 p-8">
      <div className="flex justify-between items-center mb-10">
        <h1 className="text-3xl font-extrabold text-gray-800">
          Payment 💳
        </h1>
        <LogoutButton />
      </div>

      <div className="max-w-md mx-auto bg-white rounded-2xl shadow-xl p-8">
        <h2 className="text-xl font-bold">{plan.planName}</h2>
        <p className="text-gray-600">{plan.durationDays} days</p>

        <p className="text-4xl font-extrabold text-indigo-700 my-6">
          ₹ {plan.price}
        </p>

        <select
          value={mode}
          onChange={e => setMode(e.target.value)}
          className="w-full border rounded-xl p-3 mb-6"
        >
          <option value="UPI">UPI</option>
          <option value="CARD">Card</option>
          <option value="CASH">Cash</option>
        </select>

        <button
          disabled={loading}
          onClick={handlePayment}
          className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-semibold"
        >
          {loading ? "Processing..." : "Pay Now"}
        </button>
      </div>
    </div>
  )
}

export default StudentPayment
