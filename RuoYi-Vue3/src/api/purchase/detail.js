import request from '@/utils/request'

// 查询采购明细列表
export function listDetail(query) {
  return request({
    url: '/purchase/detail/list',
    method: 'get',
    params: query
  })
}

// 查询采购明细详细
export function getDetail(detailId) {
  return request({
    url: '/purchase/detail/' + detailId,
    method: 'get'
  })
}

// 新增采购明细
export function addDetail(data) {
  return request({
    url: '/purchase/detail',
    method: 'post',
    data: data
  })
}

// 修改采购明细
export function updateDetail(data) {
  return request({
    url: '/purchase/detail',
    method: 'put',
    data: data
  })
}

// 删除采购明细
export function delDetail(detailId) {
  return request({
    url: '/purchase/detail/' + detailId,
    method: 'delete'
  })
}
