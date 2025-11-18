import { defineStore } from 'pinia'

/**
 * 从localStorage加载设置的辅助函数
 */
function loadSettings() {
  const settings = localStorage.getItem('settings')
  if (settings) {
    try {
      return JSON.parse(settings)
    } catch (e) {
      console.error('Failed to parse settings from localStorage', e)
    }
  }
  return {}
}

export const useSettingsStore = defineStore('settings', {
  state: () => {
    const loadedSettings = loadSettings()
    return {
      title: loadedSettings.title || '',
      fixedHeader: loadedSettings.fixedHeader || false,
      sidebarLogo: loadedSettings.sidebarLogo || true,
      theme: loadedSettings.theme || '',
      layout: loadedSettings.layout || 'default'
    }
  },
  
  actions: {
    /**
     * 更新设置
     */
    updateSettings(setting) {
      this.$patch(setting)
      this.saveSettings()
    },
    
    /**
     * 保存设置到localStorage
     */
    saveSettings() {
      localStorage.setItem('settings', JSON.stringify({
        title: this.title,
        fixedHeader: this.fixedHeader,
        sidebarLogo: this.sidebarLogo,
        theme: this.theme,
        layout: this.layout
      }))
    },
    
    /**
     * 设置网站标题
     */
    setTitle(title) {
      this.title = title
      document.title = title
    }
  }
})