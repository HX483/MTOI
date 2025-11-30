package com.ruoyi.report.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 报表分析Mapper接口
 * 
 * @author xihao
 * @date 2025-01-01
 */
public interface ReportMapper 
{
    /**
     * 获取总库存数量
     */
    public Long getTotalStockQuantity();

    /**
     * 获取总库存价值
     */
    public Double getTotalStockValue();

    /**
     * 获取仓库数量
     */
    public Long getWarehouseCount();

    /**
     * 获取原料种类数
     */
    public Long getMaterialCount();

    /**
     * 获取预警数量
     */
    public Long getAlertCount();

    /**
     * 按仓库统计库存
     */
    public List<Map<String, Object>> getStockByWarehouse();

    /**
     * 获取库存预警列表
     */
    public List<Map<String, Object>> getStockAlert();

    /**
     * 获取盘点总数
     */
    public Long getTotalChecks();

    /**
     * 获取进行中的盘点数
     */
    public Long getOngoingChecks();

    /**
     * 获取已完成的盘点数
     */
    public Long getCompletedChecks();

    /**
     * 获取总差异数量
     */
    public Long getTotalDiscrepancy();

    /**
     * 获取盘点明细统计
     */
    public List<Map<String, Object>> getCheckDetails();

    /**
     * 获取原料使用分析
     */
    public List<Map<String, Object>> getMaterialUsage();

    /**
     * 获取产品总数
     */
    public Long getTotalProducts();

    /**
     * 获取上架产品数
     */
    public Long getOnSaleProducts();

    /**
     * 获取热销产品数
     */
    public Long getHotProducts();

    /**
     * 按分类统计产品
     */
    public List<Map<String, Object>> getProductByCategory();
}


