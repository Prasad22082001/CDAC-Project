import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function StudentMenu() {
  const [menus, setMenus] = useState([])
  const [error, setError] = useState("")
  const navigate = useNavigate()

  useEffect(() => {
    api.get("/menu/student")
      .then(res => {
        setMenus(res.data)
        setError("")
      })
      .catch(() => {
        setMenus([])
        setError("Please select mess & complete payment to view menu")
      })
  }, [])

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="flex justify-between mb-4">
        <h1 className="text-2xl font-bold">Today's Menu 🍽️</h1>
        <LogoutButton />
      </div>

      <button
        onClick={() => navigate("/student/plan")}
        className="mb-4 bg-purple-600 text-white px-4 py-2 rounded"
      >
        Pay Mess Fees 💳
      </button>

      {error && (
        <p className="text-red-500 font-semibold mb-4">{error}</p>
      )}

      {menus.map(m => (
        <div
          key={m.menuId}
          className="bg-white p-4 mb-3 rounded shadow"
        >
          <p className="font-semibold text-lg">{m.itemName}</p>
          <p className="text-gray-600">{m.type}</p>
        </div>
      ))}
    </div>
  )
}

export default StudentMenu
