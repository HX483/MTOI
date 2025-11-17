import request from '@/utils/request'

// 查询原料信息列表
export function listInfo(query) {
  return request({
    url: '/material/info/list',
    method: 'get',
    params: query
  })
}

// 查询原料信息详细
export function getInfo(materialId) {
  return request({
    url: '/material/info/' + materialId,
    method: 'get'
  })
}

// 新增原料信息
export function addInfo(data) {
  return request({
    url: '/material/info',
    method: 'post',
    data: data
  })
}

// 修改原料信息
export function updateInfo(data) {
  return request({
    url: '/material/info',
    method: 'put',
    data: data
  })
}

// 删除原料信息
export function delInfo(materialId) {
  return request({
    url: '/material/info/' + materialId,
    method: 'delete'
  })
}
