import axios from 'axios'

const BACKEND_BASE = 'https://irms-roadshow-test.csc.com.cn'

/**
 * CAS 认证工具
 *
 * 流程说明:
 * 1. 前端路由守卫拦截每次页面访问，检查本地是否有有效 token
 * 2. 如果 URL 中带有 CAS ticket 参数，则调用后端验证接口换取 token
 * 3. 如果没有 token 也没有 ticket，跳转到 CAS 登录页
 * 4. CAS 登录成功后会带着 ticket 回调到当前页面，再走步骤 2
 */

const TOKEN_KEY = 'irms_token'
const USER_KEY = 'irms_user'

/** 获取存储的 token */
export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

/** 保存 token */
export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

/** 获取用户信息 */
export function getUser() {
  const raw = localStorage.getItem(USER_KEY)
  return raw ? JSON.parse(raw) : null
}

/** 保存用户信息 */
export function setUser(user) {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

/** 清除认证信息 */
export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_KEY)
}

/** 是否已登录 */
export function isAuthenticated() {
  return !!getToken()
}

/**
 * 跳转 CAS 登录页
 * service 参数为当前页面地址，CAS 登录成功后会回调到这个地址并附带 ticket
 */
export function redirectToCasLogin() {
  const serviceUrl = encodeURIComponent(window.location.href)
  const casLoginUrl = `${BACKEND_BASE}/api/cas/login?service=${serviceUrl}`
  window.location.href = casLoginUrl
}

/**
 * 使用 CAS ticket 向后端验证并获取 token
 * 后端会拿 ticket 去 CAS Server 校验，校验通过后返回业务 token
 */
export async function validateTicket(ticket, service) {
  try {
    const res = await axios.get(`/api/cas/validate`, {
      params: { ticket, service }
    })
    if (res.data && res.data.code === 200 && res.data.data) {
      const { token, user } = res.data.data
      if (token) {
        setToken(token)
      }
      if (user) {
        setUser(user)
      }
      return true
    }
    return false
  } catch (err) {
    console.error('CAS ticket 验证失败:', err)
    return false
  }
}

/**
 * 从 URL 中提取 CAS ticket 参数，并清理 URL
 */
export function extractTicketFromUrl() {
  const url = new URL(window.location.href)
  const ticket = url.searchParams.get('ticket')
  if (ticket) {
    // 清除 URL 中的 ticket 参数，保持地址干净
    url.searchParams.delete('ticket')
    const cleanUrl = url.pathname + (url.search || '') + (url.hash || '')
    window.history.replaceState({}, '', cleanUrl)
  }
  return ticket
}

/**
 * 获取当前页面作为 CAS service 参数（不含 ticket）
 */
export function getCurrentService() {
  const url = new URL(window.location.href)
  url.searchParams.delete('ticket')
  return url.origin + url.pathname + (url.search || '')
}

/** 创建带 token 的 axios 实例 */
export const request = axios.create({
  baseURL: '/api',
  timeout: 15000
})

request.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  response => response,
  error => {
    // 401 表示 token 失效，需要重新登录
    if (error.response && error.response.status === 401) {
      clearAuth()
      redirectToCasLogin()
    }
    return Promise.reject(error)
  }
)
