import request from '@/utils/request'

// 查询库存变动记录列表
export function listRecord(query) {
  return request({
    url: '/stock/record/list',
    method: 'get',
    params: query
  })
}

// 查询库存变动记录详细
export function getRecord(recordId) {
  return request({
    url: '/stock/record/' + recordId,
    method: 'get'
  })
}

// 新增库存变动记录
export function addRecord(data) {
  return request({
    url: '/stock/record',
    method: 'post',
    data: data
  })
}

// 修改库存变动记录
export function updateRecord(data) {
  return request({
    url: '/stock/record',
    method: 'put',
    data: data
  })
}

// 删除库存变动记录
export function delRecord(recordId) {
  return request({
    url: '/stock/record/' + recordId,
    method: 'delete'
  })
}
