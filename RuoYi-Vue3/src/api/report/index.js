import request from '@/utils/request'

// 获取库存统计报表
export function getStockStatistics() {
  return request({
    url: '/report/stock/statistics',
    method: 'get'
  })
}

// 获取库存预警报表
export function getStockAlert() {
  return request({
    url: '/report/stock/alert',
    method: 'get'
  })
}

// 获取盘点分析报表
export function getCheckAnalysis() {
  return request({
    url: '/report/check/analysis',
    method: 'get'
  })
}

// 获取原料使用分析报表
export function getMaterialUsage() {
  return request({
    url: '/report/material/usage',
    method: 'get'
  })
}

// 获取产品分析报表
export function getProductAnalysis() {
  return request({
    url: '/report/product/analysis',
    method: 'get'
  })
}

// 获取订单统计报表
export function getOrderStatistics() {
  return request({
    url: '/report/order/statistics',
    method: 'get'
  })
}

// 获取采购统计报表
export function getPurchaseStatistics() {
  return request({
    url: '/report/purchase/statistics',
    method: 'get'
  })
}

// 获取综合仪表盘数据
export function getDashboard() {
  return request({
    url: '/report/dashboard',
    method: 'get'
  })
}


