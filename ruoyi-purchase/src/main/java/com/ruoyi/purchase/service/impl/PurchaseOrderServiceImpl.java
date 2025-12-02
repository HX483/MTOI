package com.ruoyi.purchase.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.purchase.domain.PurchaseDetail;
import com.ruoyi.purchase.domain.PurchaseOrder;
import com.ruoyi.purchase.mapper.PurchaseOrderMapper;
import com.ruoyi.purchase.service.IPurchaseOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 采购订单Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-30
 */
@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService 
{
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    /**
     * 查询采购订单
     * 
     * @param purchaseId 采购订单主键
     * @return 采购订单
     */
    @Override
    public PurchaseOrder selectPurchaseOrderByPurchaseId(Long purchaseId)
    {
        return purchaseOrderMapper.selectPurchaseOrderByPurchaseId(purchaseId);
    }

    /**
     * 查询采购订单列表
     * 
     * @param purchaseOrder 采购订单
     * @return 采购订单
     */
    @Override
    public List<PurchaseOrder> selectPurchaseOrderList(PurchaseOrder purchaseOrder)
    {
        return purchaseOrderMapper.selectPurchaseOrderList(purchaseOrder);
    }

    /**
     * 新增采购订单
     * 
     * @param purchaseOrder 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        purchaseOrder.setCreateTime(DateUtils.getNowDate());
        int rows = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        insertPurchaseDetail(purchaseOrder);
        return rows;
    }

    /**
     * 修改采购订单
     * 
     * @param purchaseOrder 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder)
    {
        purchaseOrder.setUpdateTime(DateUtils.getNowDate());
        purchaseOrderMapper.deletePurchaseDetailByPurchaseId(purchaseOrder.getPurchaseId());
        insertPurchaseDetail(purchaseOrder);
        return purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * 批量删除采购订单
     * 
     * @param purchaseIds 需要删除的采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPurchaseIds(Long[] purchaseIds)
    {
        purchaseOrderMapper.deletePurchaseDetailByPurchaseIds(purchaseIds);
        return purchaseOrderMapper.deletePurchaseOrderByPurchaseIds(purchaseIds);
    }

    /**
     * 删除采购订单信息
     * 
     * @param purchaseId 采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPurchaseId(Long purchaseId)
    {
        purchaseOrderMapper.deletePurchaseDetailByPurchaseId(purchaseId);
        return purchaseOrderMapper.deletePurchaseOrderByPurchaseId(purchaseId);
    }

    /**
     * 新增采购明细信息
     * 
     * @param purchaseOrder 采购订单对象
     */
    public void insertPurchaseDetail(PurchaseOrder purchaseOrder)
    {
        List<PurchaseDetail> purchaseDetailList = purchaseOrder.getPurchaseDetailList();
        Long purchaseId = purchaseOrder.getPurchaseId();
        if (StringUtils.isNotNull(purchaseDetailList))
        {
            List<PurchaseDetail> list = new ArrayList<PurchaseDetail>();
            for (PurchaseDetail purchaseDetail : purchaseDetailList)
            {
                purchaseDetail.setPurchaseId(purchaseId);
                // 设置创建时间
                purchaseDetail.setCreateTime(DateUtils.getNowDate());
                list.add(purchaseDetail);
            }
            if (list.size() > 0)
            {
                purchaseOrderMapper.batchPurchaseDetail(list);
            }
        }
    }
}
