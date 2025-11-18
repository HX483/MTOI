import { defineStore } from 'pinia'
import { getInfo } from '@/api/system/user'
import { useUserStore } from './user'

export const useAppStore = defineStore('app', {
  state: () => ({
    device: 'desktop',
    sidebar: {
      opened: true,
      withoutAnimation: false
    },
    size: 'small'
  }),
  
  actions: {
    toggleSideBar() {
      this.sidebar.opened = !this.sidebar.opened
      this.sidebar.withoutAnimation = false
    },
    closeSideBar(withoutAnimation) {
      this.sidebar.opened = false
      this.sidebar.withoutAnimation = withoutAnimation
    },
    toggleDevice(device) {
      this.device = device
    },
    setSize(size) {
      this.size = size
    }
  }
})