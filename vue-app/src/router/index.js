import { createRouter, createWebHistory } from 'vue-router'
import {
  isAuthenticated,
  extractTicketFromUrl,
  validateTicket,
  getCurrentService,
  redirectToCasLogin
} from '../utils/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: { title: '首页', requiresAuth: true }
  },
  {
    path: '/roadshow',
    name: 'Roadshow',
    component: () => import('../views/Roadshow.vue'),
    meta: { title: '路演', requiresAuth: true }
  },
  {
    path: '/work-record',
    name: 'WorkRecord',
    component: () => import('../views/WorkRecord.vue'),
    meta: { title: '工作记录', requiresAuth: true }
  },
  {
    path: '/personnel',
    name: 'Personnel',
    component: () => import('../views/Personnel.vue'),
    meta: { title: '人员管理', requiresAuth: true }
  },
  {
    path: '/customer',
    name: 'Customer',
    component: () => import('../views/Customer.vue'),
    meta: { title: '客户管理', requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

/**
 * 全局前置守卫 - CAS 认证流程
 *
 * 1. 检查 URL 是否携带 CAS ticket
 *    - 有 ticket -> 调用后端验证 -> 验证通过则放行，失败则跳 CAS
 * 2. 检查本地是否有 token
 *    - 有 token -> 放行
 *    - 无 token -> 跳转 CAS 登录页
 */
router.beforeEach(async (to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - IRMS路演管理` : 'IRMS路演管理'

  // 不需要认证的页面直接放行
  if (!to.meta.requiresAuth) {
    return next()
  }

  // 检查 URL 中是否有 CAS 回调的 ticket
  const ticket = extractTicketFromUrl()
  if (ticket) {
    const service = getCurrentService()
    const success = await validateTicket(ticket, service)
    if (success) {
      return next()
    }
    // ticket 验证失败，重新跳转 CAS
    redirectToCasLogin()
    return
  }

  // 已有 token，放行
  if (isAuthenticated()) {
    return next()
  }

  // 未登录，跳转 CAS
  redirectToCasLogin()
})

export default router
