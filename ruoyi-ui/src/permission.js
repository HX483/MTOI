import router from './router'
import { useUserStore, useSettingsStore, usePermissionStore } from '@/stores'
import { ElMessage } from 'element-plus'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isPathMatch } from '@/utils/validate'
import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register']

const isWhiteList = (path) => {
  return whiteList.some(pattern => isPathMatch(pattern, path))
}

router.beforeEach((to, from, next) => {
  NProgress.start()
  const userStore = useUserStore()
  const settingsStore = useSettingsStore()
  const permissionStore = usePermissionStore()
  
  if (getToken()) {
    to.meta.title && settingsStore.setTitle(to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else if (isWhiteList(to.path)) {
      next()
    } else {
      if (userStore.roles.length === 0) {
        isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        userStore.getInfo().then(() => {
          isRelogin.show = false
          permissionStore.generateRoutes().then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            // Vue Router 4使用addRoute而不是addRoutes
            accessRoutes.forEach(route => {
              router.addRoute(route)
            })
            next({ ...to, replace: true }) // hack方法 确保路由已添加完成
          })
        }).catch(err => {
          userStore.logout().then(() => {
            ElMessage.error(err)
            next({ path: '/' })
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (isWhiteList(to.path)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${encodeURIComponent(to.fullPath)}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})