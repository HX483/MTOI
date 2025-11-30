package com.ruoyi.purchase.mapper;

import java.util.List;

import com.ruoyi.purchase.domain.PurchaseDetail;
import com.ruoyi.purchase.domain.PurchaseOrder;

/**
 * 采购订单Mapper接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface PurchaseOrderMapper 
{
    /**
     * 查询采购订单
     * 
     * @param purchaseId 采购订单主键
     * @return 采购订单
     */
    public PurchaseOrder selectPurchaseOrderByPurchaseId(Long purchaseId);

    /**
     * 查询采购订单列表
     * 
     * @param purchaseOrder 采购订单
     * @return 采购订单集合
     */
    public List<PurchaseOrder> selectPurchaseOrderList(PurchaseOrder purchaseOrder);

    /**
     * 新增采购订单
     * 
     * @param purchaseOrder 采购订单
     * @return 结果
     */
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 修改采购订单
     * 
     * @param purchaseOrder 采购订单
     * @return 结果
     */
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 删除采购订单
     * 
     * @param purchaseId 采购订单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseId(Long purchaseId);

    /**
     * 批量删除采购订单
     * 
     * @param purchaseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseIds(Long[] purchaseIds);

    /**
     * 批量删除采购明细
     * 
     * @param purchaseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseDetailByPurchaseIds(Long[] purchaseIds);
    
    /**
     * 批量新增采购明细
     * 
     * @param purchaseDetailList 采购明细列表
     * @return 结果
     */
    public int batchPurchaseDetail(List<PurchaseDetail> purchaseDetailList);
    

    /**
     * 通过采购订单主键删除采购明细信息
     * 
     * @param purchaseId 采购订单ID
     * @return 结果
     */
    public int deletePurchaseDetailByPurchaseId(Long purchaseId);
}
