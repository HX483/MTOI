package com.ruoyi.purchase.service;

import java.util.List;

import com.ruoyi.purchase.domain.PurchaseOrder;

/**
 * 采购订单Service接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface IPurchaseOrderService 
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
     * 批量删除采购订单
     * 
     * @param purchaseIds 需要删除的采购订单主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseIds(Long[] purchaseIds);

    /**
     * 删除采购订单信息
     * 
     * @param purchaseId 采购订单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseId(Long purchaseId);
}
