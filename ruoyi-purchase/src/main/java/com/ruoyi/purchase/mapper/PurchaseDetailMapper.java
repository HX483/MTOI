package com.ruoyi.purchase.mapper;

import java.util.List;

import com.ruoyi.purchase.domain.PurchaseDetail;

/**
 * 采购明细Mapper接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface PurchaseDetailMapper 
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
     * 删除采购明细
     * 
     * @param detailId 采购明细主键
     * @return 结果
     */
    public int deletePurchaseDetailByDetailId(Long detailId);

    /**
     * 批量删除采购明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseDetailByDetailIds(Long[] detailIds);
}
