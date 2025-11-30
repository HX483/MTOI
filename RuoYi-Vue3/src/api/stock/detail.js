import request from '@/utils/request'

// 查询库存盘点明细列表
export function listDetail(query) {
  return request({
    url: '/stock/detail/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点明细详细
export function getDetail(detailId) {
  return request({
    url: '/stock/detail/' + detailId,
    method: 'get'
  })
}

// 新增库存盘点明细
export function addDetail(data) {
  return request({
    url: '/stock/detail',
    method: 'post',
    data: data
  })
}

// 修改库存盘点明细
export function updateDetail(data) {
  return request({
    url: '/stock/detail',
    method: 'put',
    data: data
  })
}

// 删除库存盘点明细
export function delDetail(detailId) {
  return request({
    url: '/stock/detail/' + detailId,
    method: 'delete'
  })
}
