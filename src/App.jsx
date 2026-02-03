import { BrowserRouter, Routes, Route } from "react-router-dom"

import Login from "./pages/Login"

// ================= ADMIN =================
import AdminDashboard from "./pages/AdminDashboard"
import AdminAdmins from "./pages/AdminAdmins"
import AdminStudents from "./pages/AdminStudents"
import AdminVendors from "./pages/AdminVendors"
import AdminPlans from "./pages/AdminPlans"

// ================= VENDOR =================
import VendorDashboard from "./pages/VendorDashboard"
import VendorMenu from "./pages/VendorMenu"
import VendorWorkers from "./pages/VendorWorkers"
import VendorStudents from "./pages/VendorStudents"   // ✅ NEW

// ================= STUDENT =================
import StudentMenu from "./pages/StudentMenu"
import StudentSelectVendor from "./pages/StudentSelectVendor"
import StudentPlan from "./pages/StudentPlan"
import StudentPayment from "./pages/StudentPayment"
import StudentPaymentHistory from "./pages/StudentPaymentHistory"

import ProtectedRoute from "./components/ProtectedRoute"

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* ================= LOGIN ================= */}
        <Route path="/" element={<Login />} />

        {/* ================= ADMIN ================= */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/admins"
          element={
            <ProtectedRoute>
              <AdminAdmins />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/students"
          element={
            <ProtectedRoute>
              <AdminStudents />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/vendors"
          element={
            <ProtectedRoute>
              <AdminVendors />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/plans"
          element={
            <ProtectedRoute>
              <AdminPlans />
            </ProtectedRoute>
          }
        />

        {/* ================= VENDOR ================= */}
        <Route
          path="/vendor"
          element={
            <ProtectedRoute>
              <VendorDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/vendor/menu"
          element={
            <ProtectedRoute>
              <VendorMenu />
            </ProtectedRoute>
          }
        />

        <Route
          path="/vendor/workers"
          element={
            <ProtectedRoute>
              <VendorWorkers />
            </ProtectedRoute>
          }
        />

        {/* ✅ VENDOR → SELECTED STUDENTS */}
        <Route
          path="/vendor/students"
          element={
            <ProtectedRoute>
              <VendorStudents />
            </ProtectedRoute>
          }
        />

        {/* ================= STUDENT ================= */}
        <Route
          path="/student/select-vendor"
          element={
            <ProtectedRoute>
              <StudentSelectVendor />
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/plan"
          element={
            <ProtectedRoute>
              <StudentPlan />
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/payment"
          element={
            <ProtectedRoute>
              <StudentPayment />
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/payment-history"
          element={
            <ProtectedRoute>
              <StudentPaymentHistory />
            </ProtectedRoute>
          }
        />

        <Route
          path="/student/menu"
          element={
            <ProtectedRoute>
              <StudentMenu />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  )
}

export default App
