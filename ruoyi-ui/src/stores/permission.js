import { defineStore } from 'pinia'
import { constantRoutes } from '@/router'
import { getRouters } from '@/api/system/menu'
import Layout from '@/layout'
import ParentView from '@/components/ParentView'
import InnerLink from '@/components/InnerLink'

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    routes: [],
    addRoutes: [],
    defaultRoutes: [],
    topbarRoutes: [],
    sidebarRouters: []
  }),
  
  actions: {
    setRoutes(routes) {
      this.addRoutes = routes
      this.routes = constantRoutes.concat(routes)
    },
    
    setSidebarRouters(routes) {
      this.sidebarRouters = routes
    },
    
    setTopbarRoutes(routes) {
      this.topbarRoutes = routes
    },
    
    setDefaultRoutes(routes) {
      this.defaultRoutes = constantRoutes.concat(routes)
    },
    
    /**
     * 生成路由
     */
    async generateRoutes() {
      return new Promise(resolve => {
        // 获取路由数据
        getRouters().then(res => {
          const sdata = JSON.parse(JSON.stringify(res.data))
          const rdata = JSON.parse(JSON.stringify(res.data))
          const defaultData = JSON.parse(JSON.stringify(res.data))
          const sidebarRoutes = this.filterAsyncRouter(sdata)
          const rewriteRoutes = this.filterAsyncRouter(rdata, false, true)
          const defaultRoutes = this.filterAsyncRouter(defaultData)
          const topbarRoutes = this.filterAsyncRouter(rdata, true)
          
          this.setRoutes(rewriteRoutes)
          this.setSidebarRouters(sidebarRoutes)
          this.setDefaultRoutes(defaultRoutes)
          this.setTopbarRoutes(topbarRoutes)
          
          resolve(rewriteRoutes)
        })
      })
    },
    
    /**
     * 过滤异步路由表
     */
    filterAsyncRouter(asyncRouterMap, lastRouter = false, type = false) {
      return asyncRouterMap.filter(route => {
        if (type && route.children) {
          route.children = this.filterChildren(route.children)
        }
        if (route.component) {
          // Layout ParentView InnerLink组件特殊处理
          if (route.component === 'Layout') {
            route.component = Layout
          } else if (route.component === 'ParentView') {
            route.component = ParentView
          } else if (route.component === 'InnerLink') {
            route.component = InnerLink
          } else {
            route.component = this.loadView(route.component)
          }
        }
        if (route.children != null && route.children && route.children.length) {
          route.children = this.filterAsyncRouter(route.children, route, type)
        } else {
          delete route['children']
          delete route['redirect']
        }
        return true
      })
    },
    
    filterChildren(childrenMap, lastRouter = false) {
      const children = []
      childrenMap.forEach((el, index) => {
        if (el.children && el.children.length) {
          if (el.component === 'ParentView' && !lastRouter) {
            el.children.forEach(c => {
              c.path = el.path + '/' + c.path
              if (c.children && c.children.length) {
                children = children.concat(this.filterChildren(c.children, c))
                return
              }
              children.push(c)
            })
            return
          }
        }
        if (lastRouter) {
          el.path = lastRouter.path + '/' + el.path
        }
        children.push(el)
      })
      return children
    },
    
    /**
     * 加载组件
     */
    loadView(view) {
      // 在Vite中，使用动态import替代require
      return () => import(`@/views/${view}`)
    }
  }
})