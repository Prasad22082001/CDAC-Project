import { useEffect, useState } from "react"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function StudentPaymentHistory() {
  const [payments, setPayments] = useState([])

  useEffect(() => {
    api.get("/payment/my")
      .then(res => setPayments(res.data))
      .catch(() => alert("Failed to load payments"))
  }, [])

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-slate-100 p-8">
      <div className="flex justify-between items-center mb-10">
        <h1 className="text-3xl font-extrabold text-gray-800">
          Payment History 📜
        </h1>
        <LogoutButton />
      </div>

      <div className="bg-white rounded-2xl shadow-lg overflow-x-auto">
        {payments.length === 0 ? (
          <p className="p-6 text-gray-500">No payments done yet</p>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-100">
              <tr>
                <th className="p-4 text-left">Date</th>
                <th className="p-4 text-left">Amount</th>
                <th className="p-4 text-left">Mode</th>
                <th className="p-4 text-left">Status</th>
              </tr>
            </thead>
            <tbody>
              {payments.map(p => (
                <tr key={p.paymentId} className="border-t">
                  <td className="p-4">
                    {new Date(p.paymentDate).toLocaleString()}
                  </td>
                  <td className="p-4 font-semibold">₹ {p.amount}</td>
                  <td className="p-4">{p.paymentMode}</td>
                  <td className="p-4 text-green-600 font-bold">
                    {p.status}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  )
}

export default StudentPaymentHistory
