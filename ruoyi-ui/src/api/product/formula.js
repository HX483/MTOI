import request from '@/utils/request'

// 查询产品配方列表
export function listFormula(query) {
  return request({
    url: '/product/formula/list',
    method: 'get',
    params: query
  })
}

// 查询产品配方详细
export function getFormula(formulaId) {
  return request({
    url: '/product/formula/' + formulaId,
    method: 'get'
  })
}

// 新增产品配方
export function addFormula(data) {
  return request({
    url: '/product/formula',
    method: 'post',
    data: data
  })
}

// 修改产品配方
export function updateFormula(data) {
  return request({
    url: '/product/formula',
    method: 'put',
    data: data
  })
}

// 删除产品配方
export function delFormula(formulaId) {
  return request({
    url: '/product/formula/' + formulaId,
    method: 'delete'
  })
}
