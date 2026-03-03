<template>
  <div class="home">
    <header class="header">
      <h1 class="header-title">IRMS路演管理</h1>
      <span class="header-logout" @click="handleLogout">退出</span>
    </header>

    <div class="grid">
      <div
        v-for="item in modules"
        :key="item.name"
        class="grid-item"
        :style="{ background: item.bgColor }"
        @click="$router.push(item.path)"
      >
        <div class="grid-icon">{{ item.icon }}</div>
        <div class="grid-label">{{ item.label }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { clearAuth, redirectToCasLogin } from '../utils/auth'

const modules = [
  {
    name: 'roadshow',
    label: '路演',
    icon: '🎤',
    path: '/roadshow',
    bgColor: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'
  },
  {
    name: 'work-record',
    label: '工作记录',
    icon: '📋',
    path: '/work-record',
    bgColor: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)'
  },
  {
    name: 'personnel',
    label: '人员管理',
    icon: '👥',
    path: '/personnel',
    bgColor: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)'
  },
  {
    name: 'customer',
    label: '客户管理',
    icon: '🏢',
    path: '/customer',
    bgColor: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)'
  }
]

function handleLogout() {
  clearAuth()
  redirectToCasLogin()
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  background: linear-gradient(135deg, #1a73e8 0%, #0d47a1 100%);
  color: #fff;
}

.header-title {
  font-size: 18px;
  font-weight: 600;
}

.header-logout {
  font-size: 14px;
  cursor: pointer;
  padding: 4px 12px;
  border: 1px solid rgba(255, 255, 255, 0.6);
  border-radius: 16px;
}

.header-logout:active {
  opacity: 0.7;
}

.grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
  padding: 20px;
  max-width: 500px;
  margin: 0 auto;
}

.grid-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 140px;
  border-radius: 12px;
  color: #fff;
  cursor: pointer;
  transition: transform 0.15s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.grid-item:active {
  transform: scale(0.95);
}

.grid-icon {
  font-size: 40px;
  margin-bottom: 12px;
}

.grid-label {
  font-size: 16px;
  font-weight: 500;
  letter-spacing: 1px;
}
</style>
