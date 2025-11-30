package com.ruoyi.purchase.service;

import java.util.List;

import com.ruoyi.purchase.domain.PurchaseDetail;

/**
 * 采购明细Service接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface IPurchaseDetailService 
{
    /**
     * 查询采购明细
     * 
     * @param detailId 采购明细主键
     * @return 采购明细
     */
    public PurchaseDetail selectPurchaseDetailByDetailId(Long detailId);

    /**
     * 查询采购明细列表
     * 
     * @param purchaseDetail 采购明细
     * @return 采购明细集合
     */
    public List<PurchaseDetail> selectPurchaseDetailList(PurchaseDetail purchaseDetail);

    /**
     * 新增采购明细
     * 
     * @param purchaseDetail 采购明细
     * @return 结果
     */
    public int insertPurchaseDetail(PurchaseDetail purchaseDetail);

    /**
     * 修改采购明细
     * 
     * @param purchaseDetail 采购明细
     * @return 结果
     */
    public int updatePurchaseDetail(PurchaseDetail purchaseDetail);

    /**
     * 批量删除采购明细
     * 
     * @param detailIds 需要删除的采购明细主键集合
     * @return 结果
     */
    public int deletePurchaseDetailByDetailIds(Long[] detailIds);

    /**
     * 删除采购明细信息
     * 
     * @param detailId 采购明细主键
     * @return 结果
     */
    public int deletePurchaseDetailByDetailId(Long detailId);
}
