import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 8080,
    proxy: {
      '/api': {
        target: 'https://irms-roadshow-test.csc.com.cn',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
