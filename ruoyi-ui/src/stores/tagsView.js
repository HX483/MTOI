import { defineStore } from 'pinia'

export const useTagsViewStore = defineStore('tagsView', {
  state: () => ({
    visitedViews: [],
    cachedViews: []
  }),
  
  actions: {
    /**
     * 添加访问的视图
     */
    addView(view) {
      this.addVisitedView(view)
      this.addCachedView(view)
    },
    
    /**
     * 添加已访问的视图
     */
    addVisitedView(view) {
      if (this.visitedViews.some(v => v.path === view.path)) return
      if (view.meta && view.meta.affix) {
        this.visitedViews.unshift({
          ...view,
          title: view.meta.title || 'no-name'
        })
      } else {
        this.visitedViews.push({
          ...view,
          title: view.meta.title || 'no-name'
        })
      }
    },
    
    /**
     * 添加缓存的视图
     */
    addCachedView(view) {
      if (this.cachedViews.includes(view.name)) return
      if (view.meta && !view.meta.noCache) {
        this.cachedViews.push(view.name)
      }
    },
    
    /**
     * 删除视图
     */
    delView(view) {
      return new Promise(resolve => {
        this.delVisitedView(view)
        this.delCachedView(view)
        resolve({ 
          visitedViews: [...this.visitedViews],
          cachedViews: [...this.cachedViews]
        })
      })
    },
    
    /**
     * 删除已访问的视图
     */
    delVisitedView(view) {
      const index = this.visitedViews.findIndex(v => v.path === view.path)
      if (index > -1) {
        this.visitedViews.splice(index, 1)
      }
    },
    
    /**
     * 删除缓存的视图
     */
    delCachedView(view) {
      const index = this.cachedViews.indexOf(view.name)
      if (index > -1) {
        this.cachedViews.splice(index, 1)
      }
    },
    
    /**
     * 删除其他视图
     */
    delOthersViews(view) {
      return new Promise(resolve => {
        this.delOthersVisitedViews(view)
        this.delOthersCachedViews(view)
        resolve({
          visitedViews: [...this.visitedViews],
          cachedViews: [...this.cachedViews]
        })
      })
    },
    
    /**
     * 删除其他已访问的视图
     */
    delOthersVisitedViews(view) {
      this.visitedViews = this.visitedViews.filter(v => {
        return v.meta && v.meta.affix || v.path === view.path
      })
    },
    
    /**
     * 删除其他缓存的视图
     */
    delOthersCachedViews(view) {
      const index = this.cachedViews.indexOf(view.name)
      if (index > -1) {
        this.cachedViews = this.cachedViews.slice(index, index + 1)
      } else {
        this.cachedViews = []
      }
    },
    
    /**
     * 删除所有视图
     */
    delAllViews() {
      return new Promise(resolve => {
        this.delAllVisitedViews()
        this.delAllCachedViews()
        resolve({
          visitedViews: [...this.visitedViews],
          cachedViews: [...this.cachedViews]
        })
      })
    },
    
    /**
     * 删除所有已访问的视图
     */
    delAllVisitedViews() {
      // 保留固定的标签
      const affixTags = this.visitedViews.filter(tag => tag.meta && tag.meta.affix)
      this.visitedViews = affixTags
    },
    
    /**
     * 删除所有缓存的视图
     */
    delAllCachedViews() {
      this.cachedViews = []
    },
    
    /**
     * 更新已访问的视图
     */
    updateVisitedView(view) {
      for (let v of this.visitedViews) {
        if (v.path === view.path) {
          v = Object.assign(v, view)
          break
        }
      }
    },
    
    /**
     * 刷新当前标签
     */
    refreshCachedView(view) {
      const index = this.cachedViews.indexOf(view.name)
      if (index > -1) {
        this.cachedViews.splice(index, 1)
        setTimeout(() => {
          this.cachedViews.push(view.name)
        }, 100)
      }
    }
  }
})