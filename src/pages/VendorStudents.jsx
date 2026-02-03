import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import LogoutButton from "../components/LogoutButton"

function VendorStudents() {
  const [students, setStudents] = useState([])

  useEffect(() => {
    api.get("/student/vendor/my-students")
      .then(res => setStudents(res.data))
      .catch(() => setStudents([]))
  }, [])

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            My Students
          </h1>
          <p className="text-gray-500 mt-1">
            View students who selected your mess
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* STUDENT LIST */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-6xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            🎓 Student List
            <span className="ml-2 bg-purple-100 text-purple-800 text-sm font-semibold px-3 py-1 rounded-full">
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
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {students.length === 0 ? (
                <tr>
                  <td colSpan="4" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">🎓</div>
                      <p className="text-lg">No students selected your mess yet</p>
                      <p className="text-sm text-gray-400 mt-1">Students will appear here once they choose your mess</p>
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
          to="/vendor/dashboard"
          className="inline-flex items-center text-blue-600 hover:text-blue-800 font-semibold transition"
        >
          ← Back to Dashboard
        </Link>
      </div>
    </div>
  )
}

export default VendorStudents
