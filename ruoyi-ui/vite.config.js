import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import viteCompression from 'vite-plugin-compression'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd())
  const baseUrl = 'http://localhost:8080' // 后端接口
  const port = 80 // 端口
  
  return {
    // 部署生产环境和开发环境下的URL
    base: mode === "production" ? "/" : "/",
    
    // 项目根目录
    root: process.cwd(),
    
    // 构建输出目录
    build: {
      outDir: 'dist',
      assetsDir: 'static',
      sourcemap: false,
      // 消除打包大小超过500kb警告
      chunkSizeWarningLimit: 2000,
      // 分包配置
      rollupOptions: {
        output: {
          manualChunks: {
            // 拆分代码块
            'vue-vendor': ['vue', 'vue-router', 'pinia'],
            'element-plus': ['element-plus'],
          }
        }
      }
    },
    
    // 开发服务器配置
    server: {
      host: '0.0.0.0',
      port: port,
      open: true,
      proxy: {
        [env.VITE_APP_BASE_API]: {
          target: baseUrl,
          changeOrigin: true,
          rewrite: (path) => path.replace(new RegExp(`^${env.VITE_APP_BASE_API}`), '')
        },
        // springdoc proxy
        '^/v3/api-docs/(.*)': {
          target: baseUrl,
          changeOrigin: true
        }
      }
    },
    
    // 配置CSS
    css: {
      preprocessorOptions: {
        scss: {
          outputStyle: "expanded"
        }
      }
    },
    
    // 解析配置
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src')
      },
      extensions: ['.vue', '.js', '.ts', '.jsx', '.tsx', '.json']
    },
    
    // 插件配置
    plugins: [
      vue(),
      
      // Gzip压缩
      viteCompression({
        verbose: true,
        disable: false,
        threshold: 10240,
        algorithm: 'gzip',
        ext: '.gz'
      }),
      
      // SVG图标插件
      createSvgIconsPlugin({
        // 指定需要缓存的图标文件夹
        iconDirs: [resolve(__dirname, 'src/assets/icons')],
        // 指定symbolId格式
        symbolId: 'icon-[name]'
      })
    ]
  }
})