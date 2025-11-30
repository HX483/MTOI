import request from '@/utils/request'

// 查询顾客列表
export function listCustomer(query) {
  return request({
    url: '/order/customer/list',
    method: 'get',
    params: query
  })
}

// 查询顾客详细
export function getCustomer(customerId) {
  return request({
    url: '/order/customer/' + customerId,
    method: 'get'
  })
}

// 新增顾客
export function addCustomer(data) {
  return request({
    url: '/order/customer',
    method: 'post',
    data: data
  })
}

// 修改顾客
export function updateCustomer(data) {
  return request({
    url: '/order/customer',
    method: 'put',
    data: data
  })
}

// 删除顾客
export function delCustomer(customerId) {
  return request({
    url: '/order/customer/' + customerId,
    method: 'delete'
  })
}
