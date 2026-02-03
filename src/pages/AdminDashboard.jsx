import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function AdminDashboard() {
  const [counts, setCounts] = useState({
    admins: 0,
    students: 0,
    vendors: 0,
    plans: 0,
  })

  useEffect(() => {
    Promise.all([
      api.get("/admin/all"),
      api.get("/student/all"),
      api.get("/vendor/all"),
      api.get("/plan/all"),
    ])
      .then(([a, s, v, p]) =>
        setCounts({
          admins: a.data.length,
          students: s.data.length,
          vendors: v.data.length,
          plans: p.data.length,
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
            Admin Dashboard
          </h1>
          <p className="text-gray-500 mt-1">
            Manage your mess management system efficiently
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* STATS */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-12">
        <StatCard
          title="Admins"
          value={counts.admins}
          icon="👑"
          color="border-gray-600"
        />
        <StatCard
          title="Students"
          value={counts.students}
          icon="🎓"
          color="border-blue-600"
        />
        <StatCard
          title="Vendors"
          value={counts.vendors}
          icon="🍽️"
          color="border-green-600"
        />
        <StatCard
          title="Mess Plans"
          value={counts.plans}
          icon="📋"
          color="border-purple-600"
        />
      </div>

      {/* NAVIGATION */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <NavButton
          to="/admin/admins"
          label="Manage Admins"
          color="border-gray-600"
        />
        <NavButton
          to="/admin/students"
          label="Manage Students"
          color="border-blue-600"
        />
        <NavButton
          to="/admin/vendors"
          label="Manage Vendors"
          color="border-green-600"
        />
        <NavButton
          to="/admin/plans"
          label="Manage Mess Plans"
          color="border-purple-600"
        />
      </div>
    </div>
  )
}

export default AdminDashboard
