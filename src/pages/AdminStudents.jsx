import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function AdminStudents() {
  const [students, setStudents] = useState([])
  const [form, setForm] = useState({
    name: "",
    email: "",
    contact: "",
    password: ""
  })

  // 🔄 Load students
  const loadStudents = () => {
    api.get("/student/all")
      .then(res => setStudents(res.data))
      .catch(() => setStudents([]))
  }

  useEffect(() => {
    loadStudents()
  }, [])

  // ➕ Add student
  const handleAdd = () => {
    if (!form.name || !form.email || !form.contact || !form.password) {
      alert("Fill all fields")
      return
    }

    api.post("/student/add", form)
      .then(() => {
        setForm({ name: "", email: "", contact: "", password: "" })
        loadStudents()
      })
      .catch(() => alert("Add failed"))
  }

  // ❌ Delete student
  const handleDelete = id => {
    if (!window.confirm("Delete this student?")) return

    api.delete(`/student/delete/${id}`)
      .then(loadStudents)
      .catch(() => alert("Delete failed"))
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Manage Students
          </h1>
          <p className="text-gray-500 mt-1">
            Add, view and manage student accounts
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* ADD STUDENT FORM */}
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-12 max-w-6xl mx-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 flex items-center">
          🎓 Add New Student
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Name</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
              placeholder="Enter student name"
              value={form.name}
              onChange={e => setForm({ ...form, name: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Email</label>
            <input
              type="email"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
              placeholder="student@example.com"
              value={form.email}
              onChange={e => setForm({ ...form, email: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Contact</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
              placeholder="Phone number"
              value={form.contact}
              onChange={e => setForm({ ...form, contact: e.target.value })}
            />
          </div>
          <div className="lg:col-span-2">
            <label className="block text-sm font-semibold text-gray-700 mb-2">Password</label>
            <input
              type="password"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent transition"
              placeholder="Enter secure password"
              value={form.password}
              onChange={e => setForm({ ...form, password: e.target.value })}
            />
          </div>
          <div className="lg:col-span-3 flex items-end">
            <button
              onClick={handleAdd}
              className="w-full bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-blue-500 text-lg flex items-center justify-center"
            >
              ➕ Add Student
            </button>
          </div>
        </div>
      </div>

      {/* STUDENT TABLE */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-6xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            📋 Student List
            <span className="ml-2 bg-blue-100 text-blue-800 text-sm font-semibold px-3 py-1 rounded-full">
              {students.length} student(s)
            </span>
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Name</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Email</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Contact</th>
                <th className="px-8 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {students.length === 0 ? (
                <tr>
                  <td colSpan="5" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">🎓</div>
                      <p className="text-lg">No students found</p>
                      <p className="text-sm text-gray-400 mt-1">Add your first student above</p>
                    </div>
                  </td>
                </tr>
              ) : (
                students.map(s => (
                  <tr
                    key={s.studentId}
                    className="hover:bg-gray-50 transition-colors"
                  >
                    <td className="px-8 py-6 font-mono text-sm text-gray-900 bg-gray-50">{s.studentId}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">{s.name}</td>
                    <td className="px-8 py-6 text-sm text-gray-700 max-w-md truncate">{s.email}</td>
                    <td className="px-8 py-6 text-sm text-gray-700">{s.contact}</td>
                    <td className="px-8 py-6 text-center">
                      <button
                        onClick={() => handleDelete(s.studentId)}
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

export default AdminStudents
