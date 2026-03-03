<template>
  <div class="page">
    <header class="page-header">
      <span class="back" @click="$router.push('/')">← 返回</span>
      <h1>工作记录</h1>
      <span></span>
    </header>
    <div class="page-body">
      <p class="placeholder">工作记录模块开发中...</p>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.page { min-height: 100vh; background: #f5f5f5; }
.page-header {
  display: flex; align-items: center; justify-content: space-between;
  padding: 16px 20px; background: linear-gradient(135deg, #f093fb, #f5576c); color: #fff;
}
.page-header h1 { font-size: 18px; font-weight: 600; }
.back { font-size: 14px; cursor: pointer; }
.page-body { padding: 40px 20px; text-align: center; }
.placeholder { color: #999; font-size: 16px; }
</style>
