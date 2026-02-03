import axios from "axios"

const api = axios.create({
  baseURL: "http://localhost:8080",
})

// 🔐 Automatically attach JWT token to secured requests
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem("token")

    const publicUrls = [
      "/admin/login",
      "/vendor/login",
      "/student/login",
    ]

    // 🔑 Add Authorization header only for protected APIs
    if (token && !publicUrls.some(url => config.url.includes(url))) {
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  error => Promise.reject(error)
)

export default api
