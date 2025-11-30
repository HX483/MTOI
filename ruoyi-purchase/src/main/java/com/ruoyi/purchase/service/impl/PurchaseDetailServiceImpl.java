package com.ruoyi.purchase.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.purchase.domain.PurchaseDetail;
import com.ruoyi.purchase.mapper.PurchaseDetailMapper;
import com.ruoyi.purchase.service.IPurchaseDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 采购明细Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-30
 */
@Service
public class PurchaseDetailServiceImpl implements IPurchaseDetailService 
{
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;

    /**
     * 查询采购明细
     * 
     * @param detailId 采购明细主键
     * @return 采购明细
     */
    @Override
    public PurchaseDetail selectPurchaseDetailByDetailId(Long detailId)
    {
        return purchaseDetailMapper.selectPurchaseDetailByDetailId(detailId);
    }

    /**
     * 查询采购明细列表
     * 
     * @param purchaseDetail 采购明细
     * @return 采购明细
     */
    @Override
    public List<PurchaseDetail> selectPurchaseDetailList(PurchaseDetail purchaseDetail)
    {
        return purchaseDetailMapper.selectPurchaseDetailList(purchaseDetail);
    }

    /**
     * 新增采购明细
     * 
     * @param purchaseDetail 采购明细
     * @return 结果
     */
    @Override
    public int insertPurchaseDetail(PurchaseDetail purchaseDetail)
    {
        purchaseDetail.setCreateTime(DateUtils.getNowDate());
        return purchaseDetailMapper.insertPurchaseDetail(purchaseDetail);
    }

    /**
     * 修改采购明细
     * 
     * @param purchaseDetail 采购明细
     * @return 结果
     */
    @Override
    public int updatePurchaseDetail(PurchaseDetail purchaseDetail)
    {
        purchaseDetail.setUpdateTime(DateUtils.getNowDate());
        return purchaseDetailMapper.updatePurchaseDetail(purchaseDetail);
    }

    /**
     * 批量删除采购明细
     * 
     * @param detailIds 需要删除的采购明细主键
     * @return 结果
     */
    @Override
    public int deletePurchaseDetailByDetailIds(Long[] detailIds)
    {
        return purchaseDetailMapper.deletePurchaseDetailByDetailIds(detailIds);
    }

    /**
     * 删除采购明细信息
     * 
     * @param detailId 采购明细主键
     * @return 结果
     */
    @Override
    public int deletePurchaseDetailByDetailId(Long detailId)
    {
        return purchaseDetailMapper.deletePurchaseDetailByDetailId(detailId);
    }
}
