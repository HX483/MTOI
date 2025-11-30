import request from '@/utils/request'

// 查询订单明细列表
export function listDetail(query) {
  return request({
    url: '/order/detail/list',
    method: 'get',
    params: query
  })
}

// 查询订单明细详细
export function getDetail(detailId) {
  return request({
    url: '/order/detail/' + detailId,
    method: 'get'
  })
}

// 新增订单明细
export function addDetail(data) {
  return request({
    url: '/order/detail',
    method: 'post',
    data: data
  })
}

// 修改订单明细
export function updateDetail(data) {
  return request({
    url: '/order/detail',
    method: 'put',
    data: data
  })
}

// 删除订单明细
export function delDetail(detailId) {
  return request({
    url: '/order/detail/' + detailId,
    method: 'delete'
  })
}
