import request from '@/utils/request'

// 查询采购订单列表
export function listOrder(query) {
  return request({
    url: '/purchase/order/list',
    method: 'get',
    params: query
  })
}

// 查询采购订单详细
export function getOrder(purchaseId) {
  return request({
    url: '/purchase/order/' + purchaseId,
    method: 'get'
  })
}

// 新增采购订单
export function addOrder(data) {
  return request({
    url: '/purchase/order',
    method: 'post',
    data: data
  })
}

// 修改采购订单
export function updateOrder(data) {
  return request({
    url: '/purchase/order',
    method: 'put',
    data: data
  })
}

// 删除采购订单
export function delOrder(purchaseId) {
  return request({
    url: '/purchase/order/' + purchaseId,
    method: 'delete'
  })
}
