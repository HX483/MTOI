package com.ruoyi.report.service;

import java.util.List;
import java.util.Map;

/**
 * 报表分析Service接口
 * 
 * @author xihao
 * @date 2025-01-01
 */
public interface IReportService 
{
    /**
     * 获取库存统计报表
     * 
     * @return 库存统计数据
     */
    public Map<String, Object> getStockStatistics();

    /**
     * 获取库存预警报表
     * 
     * @return 库存预警列表
     */
    public List<Map<String, Object>> getStockAlert();

    /**
     * 获取盘点分析报表
     * 
     * @return 盘点分析数据
     */
    public Map<String, Object> getCheckAnalysis();

    /**
     * 获取原料使用分析报表
     * 
     * @return 原料使用分析列表
     */
    public List<Map<String, Object>> getMaterialUsage();

    /**
     * 获取产品分析报表
     * 
     * @return 产品分析数据
     */
    public Map<String, Object> getProductAnalysis();

    /**
     * 获取综合仪表盘数据
     * 
     * @return 仪表盘数据
     */
    public Map<String, Object> getDashboard();
}


