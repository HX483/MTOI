import request from '@/utils/request'

// 查询产品属性列表
export function listAttribute(query) {
  return request({
    url: '/product/attribute/list',
    method: 'get',
    params: query
  })
}

// 查询产品属性详细
export function getAttribute(attributeId) {
  return request({
    url: '/product/attribute/' + attributeId,
    method: 'get'
  })
}

// 新增产品属性
export function addAttribute(data) {
  return request({
    url: '/product/attribute',
    method: 'post',
    data: data
  })
}

// 修改产品属性
export function updateAttribute(data) {
  return request({
    url: '/product/attribute',
    method: 'put',
    data: data
  })
}

// 删除产品属性
export function delAttribute(attributeId) {
  return request({
    url: '/product/attribute/' + attributeId,
    method: 'delete'
  })
}
