import request from '@/utils/request'

// 查询原料分类列表
export function listCategory(query) {
  return request({
    url: '/material/category/list',
    method: 'get',
    params: query
  })
}

// 查询原料分类详细
export function getCategory(categoryId) {
  return request({
    url: '/material/category/' + categoryId,
    method: 'get'
  })
}

// 新增原料分类
export function addCategory(data) {
  return request({
    url: '/material/category',
    method: 'post',
    data: data
  })
}

// 修改原料分类
export function updateCategory(data) {
  return request({
    url: '/material/category',
    method: 'put',
    data: data
  })
}

// 删除原料分类
export function delCategory(categoryId) {
  return request({
    url: '/material/category/' + categoryId,
    method: 'delete'
  })
}
