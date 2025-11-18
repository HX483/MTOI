import { defineStore } from 'pinia'
import { getDicts } from '@/api/system/dict/data'

export const useDictStore = defineStore('dict', {
  state: () => ({
    dictCache: new Map()
  }),
  
  actions: {
    /**
     * 根据字典类型加载字典数据
     */
    async getDict(dictType) {
      return new Promise((resolve, reject) => {
        if (this.dictCache.has(dictType)) {
          resolve(this.dictCache.get(dictType))
        } else {
          getDicts(dictType).then(res => {
            const data = res.data
            this.dictCache.set(dictType, data)
            resolve(data)
          }).catch(error => {
            reject(error)
          })
        }
      })
    },
    
    /**
     * 清除字典缓存
     */
    clearDictCache() {
      this.dictCache.clear()
    },
    
    /**
     * 根据字典类型和键值获取字典标签
     */
    getDictLabel(dictType, dictValue) {
      if (!dictValue || dictValue === undefined) {
        return ''
      }
      const dictData = this.dictCache.get(dictType)
      if (!dictData) {
        return ''
      }
      const node = dictData.find(item => item.dictValue === dictValue)
      return node ? node.dictLabel : ''
    },
    
    /**
     * 根据字典类型和标签获取字典键值
     */
    getDictValue(dictType, dictLabel) {
      if (!dictLabel) {
        return ''
      }
      const dictData = this.dictCache.get(dictType)
      if (!dictData) {
        return ''
      }
      const node = dictData.find(item => item.dictLabel === dictLabel)
      return node ? node.dictValue : ''
    }
  }
})