import request from '@/utils/request'

// 查询订单状态历史列表
export function listHistory(query) {
  return request({
    url: '/order/history/list',
    method: 'get',
    params: query
  })
}

// 查询订单状态历史详细
export function getHistory(historyId) {
  return request({
    url: '/order/history/' + historyId,
    method: 'get'
  })
}

// 新增订单状态历史
export function addHistory(data) {
  return request({
    url: '/order/history',
    method: 'post',
    data: data
  })
}

// 修改订单状态历史
export function updateHistory(data) {
  return request({
    url: '/order/history',
    method: 'put',
    data: data
  })
}

// 删除订单状态历史
export function delHistory(historyId) {
  return request({
    url: '/order/history/' + historyId,
    method: 'delete'
  })
}
