package com.ruoyi.report.controller;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.report.service.IReportService;

/**
 * 报表分析Controller
 *
 * @author xihao
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController
{
    @Autowired
    private IReportService reportService;

    /**
     * 获取库存统计报表
     */
    @PreAuthorize("@ss.hasPermi('report:stock:list')")
    @GetMapping("/stock/statistics")
    public AjaxResult getStockStatistics()
    {
        Map<String, Object> statistics = reportService.getStockStatistics();
        return success(statistics);
    }

    /**
     * 获取库存预警报表
     */
    @PreAuthorize("@ss.hasPermi('report:stock:list')")
    @GetMapping("/stock/alert")
    public AjaxResult getStockAlert()
    {
        List<Map<String, Object>> alertList = reportService.getStockAlert();
        return success(alertList);
    }

    /**
     * 获取盘点分析报表
     */
    @PreAuthorize("@ss.hasPermi('report:check:list')")
    @GetMapping("/check/analysis")
    public AjaxResult getCheckAnalysis()
    {
        Map<String, Object> analysis = reportService.getCheckAnalysis();
        return success(analysis);
    }

    /**
     * 获取原料使用分析报表
     */
    @PreAuthorize("@ss.hasPermi('report:material:list')")
    @GetMapping("/material/usage")
    public AjaxResult getMaterialUsage()
    {
        List<Map<String, Object>> usageList = reportService.getMaterialUsage();
        return success(usageList);
    }

    /**
     * 获取产品分析报表
     */
    @PreAuthorize("@ss.hasPermi('report:product:list')")
    @GetMapping("/product/analysis")
    public AjaxResult getProductAnalysis()
    {
        Map<String, Object> analysis = reportService.getProductAnalysis();
        return success(analysis);
    }

    /**
     * 获取订单统计报表
     */
    @PreAuthorize("@ss.hasPermi('report:order:list')")
    @GetMapping("/order/statistics")
    public AjaxResult getOrderStatistics()
    {
        Map<String, Object> statistics = reportService.getOrderStatistics();
        return success(statistics);
    }

    /**
     * 获取采购统计报表
     */
    @PreAuthorize("@ss.hasPermi('report:purchase:list')")
    @GetMapping("/purchase/statistics")
    public AjaxResult getPurchaseStatistics()
    {
        Map<String, Object> statistics = reportService.getPurchaseStatistics();
        return success(statistics);
    }

    /**
     * 获取综合仪表盘数据
     */
    @PreAuthorize("@ss.hasPermi('report:dashboard:list')")
    @GetMapping("/dashboard")
    public AjaxResult getDashboard()
    {
        Map<String, Object> dashboard = reportService.getDashboard();
        return success(dashboard);
    }
}


