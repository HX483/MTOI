import request from '@/utils/request'

// 查询库存盘点列表
export function listCheck(query) {
  return request({
    url: '/stock/check/list',
    method: 'get',
    params: query
  })
}

// 查询库存盘点详细
export function getCheck(checkId) {
  return request({
    url: '/stock/check/' + checkId,
    method: 'get'
  })
}

// 新增库存盘点
export function addCheck(data) {
  return request({
    url: '/stock/check',
    method: 'post',
    data: data
  })
}

// 修改库存盘点
export function updateCheck(data) {
  return request({
    url: '/stock/check',
    method: 'put',
    data: data
  })
}

// 删除库存盘点
export function delCheck(checkId) {
  return request({
    url: '/stock/check/' + checkId,
    method: 'delete'
  })
}

// 预生成盘点单号
export function generateCheckNo() {
  return request({
    url: '/stock/check/generateCheckNo',
    method: 'get'
  })
}
