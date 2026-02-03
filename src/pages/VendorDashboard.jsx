import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function VendorDashboard() {
  const [counts, setCounts] = useState({
    menuItems: 0,
    workers: 0,
    students: 0,
  })

  useEffect(() => {
    Promise.all([
      api.get("/vendor/menu/all"),
      api.get("/vendor/workers/all"), 
      api.get("/vendor/students/all"),
    ])
      .then(([m, w, s]) =>
        setCounts({
          menuItems: m.data.length,
          workers: w.data.length,
          students: s.data.length,
        })
      )
      .catch(() => {})
  }, [])

  const StatCard = ({ title, value, icon, color }) => (
    <div
      className={`bg-white rounded-2xl shadow-lg p-6 hover:scale-[1.03] transition border-l-4 ${color}`}
    >
      <h2 className="text-xl font-semibold text-gray-800 flex items-center">
        {icon}
        <span className="ml-2">{title}</span>
      </h2>
      <p className="text-4xl font-extrabold text-gray-800 mt-2">{value}</p>
    </div>
  )

  const NavButton = ({ to, label, color }) => (
    <Link
      to={to}
      className={`bg-white rounded-2xl shadow-lg p-6 hover:scale-[1.03] transition border-l-4 ${color}`}
    >
      <h2 className="text-xl font-semibold text-gray-800">{label}</h2>
      <p className="text-gray-500 mt-2">Manage {label.toLowerCase()}</p>
    </Link>
  )

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Vendor Dashboard
          </h1>
          <p className="text-gray-500 mt-1">
            Manage your mess efficiently
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* STATS */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
        <StatCard
          title="Menu Items"
          value={counts.menuItems}
          icon="🍽️"
          color="border-green-600"
        />
        <StatCard
          title="Workers"
          value={counts.workers}
          icon="👷"
          color="border-blue-600"
        />
        <StatCard
          title="Students"
          value={counts.students}
          icon="🎓"
          color="border-purple-600"
        />
      </div>

      {/* NAVIGATION */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <NavButton
          to="/vendor/menu"
          label="Manage Menu"
          color="border-green-600"
        />
        <NavButton
          to="/vendor/workers"
          label="Manage Workers"
          color="border-blue-600"
        />
        <NavButton
          to="/vendor/students"
          label="My Students"
          color="border-purple-600"
        />
      </div>
    </div>
  )
}

export default VendorDashboard
