import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import api from "../services/api"
import { jwtDecode } from "jwt-decode"
import LogoutButton from "../components/LogoutButton"

function VendorMenu() {
  const [menus, setMenus] = useState([])
  const [itemName, setItemName] = useState("")
  const [type, setType] = useState("VEG")
  const [price, setPrice] = useState("")

  const token = localStorage.getItem("token")
  const decoded = token ? jwtDecode(token) : null
  const vendorId = decoded?.user_id   // ✅ FIXED

  const fetchMenus = () => {
    api.get("/menu/all").then(res => {
      setMenus(res.data.filter(m => m.vendorId === vendorId))
    })
  }

  useEffect(() => {
    if (vendorId) fetchMenus()
  }, [vendorId])

  const handleAddMenu = () => {
    api.post("/menu/add", {
      itemName,
      type,
      price: Number(price),
      vendorId
    }).then(() => {
      setItemName("")
      setPrice("")
      fetchMenus()
    })
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-3xl font-extrabold text-gray-800">
            Manage Menu
          </h1>
          <p className="text-gray-500 mt-1">
            Add, update and manage your menu items
          </p>
        </div>
        <LogoutButton />
      </div>

      {/* ADD MENU FORM */}
      <div className="bg-white rounded-2xl shadow-lg p-8 mb-12 max-w-4xl mx-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-8 flex items-center">
          🍽️ Add New Menu Item
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-1 lg:grid-cols-4 gap-6 items-end">
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Item Name</label>
            <input
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition"
              placeholder="Enter menu item name"
              value={itemName}
              onChange={e => setItemName(e.target.value)}
            />
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Type</label>
            <select
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition bg-white"
              value={type}
              onChange={e => setType(e.target.value)}
            >
              <option>VEG</option>
              <option>NON_VEG</option>
              <option>BOTH</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-semibold text-gray-700 mb-2">Price (₹)</label>
            <input
              type="number"
              className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-transparent transition"
              placeholder="0"
              value={price}
              onChange={e => setPrice(e.target.value)}
            />
          </div>
          <div>
            <button
              onClick={handleAddMenu}
              className="w-full bg-gradient-to-r from-green-600 to-green-700 hover:from-green-700 hover:to-green-800 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:scale-[1.02] transition transform border-l-4 border-green-500 text-lg flex items-center justify-center"
              disabled={!itemName || !price}
            >
              ➕ Add Menu Item
            </button>
          </div>
        </div>
      </div>

      {/* MENU LIST */}
      <div className="bg-white rounded-2xl shadow-lg overflow-hidden max-w-4xl mx-auto">
        <div className="bg-gradient-to-r from-gray-50 to-gray-100 px-8 py-6 border-b">
          <h2 className="text-2xl font-bold text-gray-800 flex items-center">
            📋 Menu Items
            <span className="ml-2 bg-green-100 text-green-800 text-sm font-semibold px-3 py-1 rounded-full">
              {menus.length} item(s)
            </span>
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Item Name</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Type</th>
                <th className="px-8 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Price</th>
                <th className="px-8 py-4 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {menus.length === 0 ? (
                <tr>
                  <td colSpan="5" className="px-8 py-12 text-center text-gray-500">
                    <div className="flex flex-col items-center">
                      <div className="text-4xl mb-2">🍽️</div>
                      <p className="text-lg">No menu items found</p>
                      <p className="text-sm text-gray-400 mt-1">Add your first menu item above</p>
                    </div>
                  </td>
                </tr>
              ) : (
                menus.map(m => (
                  <tr
                    key={m.menuId}
                    className="hover:bg-gray-50 transition-colors"
                  >
                    <td className="px-8 py-6 font-mono text-sm text-gray-900 bg-gray-50">{m.menuId}</td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">{m.itemName}</td>
                    <td className="px-8 py-6 text-sm text-gray-700">
                      <span className={`px-2 py-1 rounded-full text-xs font-semibold ${
                        m.type === 'VEG' ? 'bg-green-100 text-green-800' :
                        m.type === 'NON_VEG' ? 'bg-red-100 text-red-800' : 
                        'bg-purple-100 text-purple-800'
                      }`}>
                        {m.type}
                      </span>
                    </td>
                    <td className="px-8 py-6 text-sm font-semibold text-gray-900">₹{m.price}</td>
                    <td className="px-8 py-6 text-center">
                      <button
                        onClick={() => api.delete(`/menu/delete/${m.menuId}`).then(fetchMenus)}
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
      <div className="mt-12 text-center max-w-4xl mx-auto">
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

export default VendorMenu
