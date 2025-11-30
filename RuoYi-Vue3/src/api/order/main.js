import request from '@/utils/request'

// 查询客户订单列表
export function listMain(query) {
  return request({
    url: '/order/main/list',
    method: 'get',
    params: query
  })
}

// 查询客户订单详细
export function getMain(orderId) {
  return request({
    url: '/order/main/' + orderId,
    method: 'get'
  })
}

// 新增客户订单
export function addMain(data) {
  return request({
    url: '/order/main',
    method: 'post',
    data: data
  })
}

// 修改客户订单
export function updateMain(data) {
  return request({
    url: '/order/main',
    method: 'put',
    data: data
  })
}

// 删除客户订单
export function delMain(orderId) {
  return request({
    url: '/order/main/' + orderId,
    method: 'delete'
  })
}
