package com.ruoyi.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.mapper.ReportMapper;
import com.ruoyi.report.service.IReportService;

/**
 * 报表分析Service业务层处理
 * 
 * @author xihao
 * @date 2025-01-01
 */
@Service
public class ReportServiceImpl implements IReportService 
{
    @Autowired
    private ReportMapper reportMapper;

    /**
     * 获取库存统计报表
     */
    @Override
    public Map<String, Object> getStockStatistics()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 总库存数量
        Long totalQuantity = reportMapper.getTotalStockQuantity();
        result.put("totalQuantity", totalQuantity != null ? totalQuantity : 0);
        
        // 总库存价值（需要根据原料价格计算）
        Double totalValue = reportMapper.getTotalStockValue();
        result.put("totalValue", totalValue != null ? totalValue : 0.0);
        
        // 仓库数量
        Long warehouseCount = reportMapper.getWarehouseCount();
        result.put("warehouseCount", warehouseCount != null ? warehouseCount : 0);
        
        // 原料种类数
        Long materialCount = reportMapper.getMaterialCount();
        result.put("materialCount", materialCount != null ? materialCount : 0);
        
        // 预警数量
        Long alertCount = reportMapper.getAlertCount();
        result.put("alertCount", alertCount != null ? alertCount : 0);
        
        // 按仓库统计
        List<Map<String, Object>> warehouseStats = reportMapper.getStockByWarehouse();
        result.put("warehouseStats", warehouseStats);
        
        return result;
    }

    /**
     * 获取库存预警报表
     */
    @Override
    public List<Map<String, Object>> getStockAlert()
    {
        return reportMapper.getStockAlert();
    }

    /**
     * 获取盘点分析报表
     */
    @Override
    public Map<String, Object> getCheckAnalysis()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 盘点总数
        Long totalChecks = reportMapper.getTotalChecks();
        result.put("totalChecks", totalChecks != null ? totalChecks : 0);
        
        // 进行中的盘点
        Long ongoingChecks = reportMapper.getOngoingChecks();
        result.put("ongoingChecks", ongoingChecks != null ? ongoingChecks : 0);
        
        // 已完成的盘点
        Long completedChecks = reportMapper.getCompletedChecks();
        result.put("completedChecks", completedChecks != null ? completedChecks : 0);
        
        // 总差异数量
        Long totalDiscrepancy = reportMapper.getTotalDiscrepancy();
        result.put("totalDiscrepancy", totalDiscrepancy != null ? totalDiscrepancy : 0);
        
        // 盘点明细统计
        List<Map<String, Object>> checkDetails = reportMapper.getCheckDetails();
        result.put("checkDetails", checkDetails);
        
        return result;
    }

    /**
     * 获取原料使用分析报表
     */
    @Override
    public List<Map<String, Object>> getMaterialUsage()
    {
        return reportMapper.getMaterialUsage();
    }

    /**
     * 获取产品分析报表
     */
    @Override
    public Map<String, Object> getProductAnalysis()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 产品总数
        Long totalProducts = reportMapper.getTotalProducts();
        result.put("totalProducts", totalProducts != null ? totalProducts : 0);
        
        // 上架产品数
        Long onSaleProducts = reportMapper.getOnSaleProducts();
        result.put("onSaleProducts", onSaleProducts != null ? onSaleProducts : 0);
        
        // 热销产品数
        Long hotProducts = reportMapper.getHotProducts();
        result.put("hotProducts", hotProducts != null ? hotProducts : 0);
        
        // 按分类统计
        List<Map<String, Object>> categoryStats = reportMapper.getProductByCategory();
        result.put("categoryStats", categoryStats);
        
        return result;
    }
    
    /**
     * 获取订单统计报表
     */
    @Override
    public Map<String, Object> getOrderStatistics()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 订单总数
        Long totalOrders = reportMapper.getTotalOrders();
        result.put("totalOrders", totalOrders != null ? totalOrders : 0);
        
        // 待处理订单数
        Long pendingOrders = reportMapper.getPendingOrders();
        result.put("pendingOrders", pendingOrders != null ? pendingOrders : 0);
        
        // 已完成订单数
        Long completedOrders = reportMapper.getCompletedOrders();
        result.put("completedOrders", completedOrders != null ? completedOrders : 0);
        
        // 本月订单金额
        Double monthOrderAmount = reportMapper.getMonthOrderAmount();
        result.put("monthOrderAmount", monthOrderAmount != null ? monthOrderAmount : 0.0);
        
        // 按客户统计
        List<Map<String, Object>> customerStats = reportMapper.getOrderByCustomer();
        result.put("customerStats", customerStats);
        
        return result;
    }
    
    /**
     * 获取采购统计报表
     */
    @Override
    public Map<String, Object> getPurchaseStatistics()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 采购订单总数
        Long totalPurchases = reportMapper.getTotalPurchases();
        result.put("totalPurchases", totalPurchases != null ? totalPurchases : 0);
        
        // 待处理采购订单数
        Long pendingPurchases = reportMapper.getPendingPurchases();
        result.put("pendingPurchases", pendingPurchases != null ? pendingPurchases : 0);
        
        // 已完成采购订单数
        Long completedPurchases = reportMapper.getCompletedPurchases();
        result.put("completedPurchases", completedPurchases != null ? completedPurchases : 0);
        
        // 本月采购金额
        Double monthPurchaseAmount = reportMapper.getMonthPurchaseAmount();
        result.put("monthPurchaseAmount", monthPurchaseAmount != null ? monthPurchaseAmount : 0.0);
        
        // 按供应商统计
        List<Map<String, Object>> supplierStats = reportMapper.getPurchaseBySupplier();
        result.put("supplierStats", supplierStats);
        
        return result;
    }

    /**
     * 获取综合仪表盘数据
     */
    @Override
    public Map<String, Object> getDashboard()
    {
        Map<String, Object> dashboard = new HashMap<>();
        
        // 库存统计
        dashboard.put("stockStatistics", getStockStatistics());
        
        // 盘点分析
        dashboard.put("checkAnalysis", getCheckAnalysis());
        
        // 产品分析
        dashboard.put("productAnalysis", getProductAnalysis());
        
        // 订单统计
        dashboard.put("orderStatistics", getOrderStatistics());
        
        // 采购统计
        dashboard.put("purchaseStatistics", getPurchaseStatistics());
        
        // 预警信息
        dashboard.put("alerts", getStockAlert());
        
        return dashboard;
    }
}


