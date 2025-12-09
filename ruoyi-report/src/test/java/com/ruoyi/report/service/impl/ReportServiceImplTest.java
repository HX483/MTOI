package com.ruoyi.report.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.report.mapper.ReportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 报告服务实现类单元测试
 * 
 * @author xihao
 * @date 2025-11-26
 */
@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportServiceImpl reportServiceImpl;

    private Map<String, Object> mockStockStats;
    private List<Map<String, Object>> mockAlertList;
    private Map<String, Object> mockCheckAnalysis;
    private List<Map<String, Object>> mockMaterialUsageList;
    private Map<String, Object> mockProductAnalysis;
    private Map<String, Object> mockOrderStatistics;
    private Map<String, Object> mockPurchaseStatistics;
    private Map<String, Object> mockDashboard;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        mockStockStats = new HashMap<>();
        mockStockStats.put("totalQuantity", 1000L);
        mockStockStats.put("totalValue", 100000.0);
        mockStockStats.put("warehouseCount", 5L);
        mockStockStats.put("materialCount", 20L);
        mockStockStats.put("alertCount", 3L);
        
        List<Map<String, Object>> warehouseStats = new ArrayList<>();
        Map<String, Object> warehouse1 = new HashMap<>();
        warehouse1.put("warehouseId", 1L);
        warehouse1.put("warehouseName", "主仓库");
        warehouse1.put("stockQuantity", 500L);
        warehouseStats.add(warehouse1);
        mockStockStats.put("warehouseStats", warehouseStats);

        mockAlertList = new ArrayList<>();
        Map<String, Object> alert1 = new HashMap<>();
        alert1.put("materialId", 1L);
        alert1.put("materialName", "测试原料1");
        alert1.put("currentStock", 10);
        alert1.put("alertThreshold", 50);
        mockAlertList.add(alert1);

        mockCheckAnalysis = new HashMap<>();
        mockCheckAnalysis.put("totalChecks", 10L);
        mockCheckAnalysis.put("ongoingChecks", 2L);
        mockCheckAnalysis.put("completedChecks", 8L);
        mockCheckAnalysis.put("totalDiscrepancy", 5L);
        
        List<Map<String, Object>> checkDetails = new ArrayList<>();
        Map<String, Object> checkDetail1 = new HashMap<>();
        checkDetail1.put("checkId", 1L);
        checkDetail1.put("checkDate", "2025-11-26");
        checkDetail1.put("discrepancyCount", 2L);
        checkDetails.add(checkDetail1);
        mockCheckAnalysis.put("checkDetails", checkDetails);

        mockMaterialUsageList = new ArrayList<>();
        Map<String, Object> usage1 = new HashMap<>();
        usage1.put("materialId", 1L);
        usage1.put("materialName", "测试原料1");
        usage1.put("usageCount", 150L);
        mockMaterialUsageList.add(usage1);

        mockProductAnalysis = new HashMap<>();
        mockProductAnalysis.put("totalProducts", 50L);
        mockProductAnalysis.put("onSaleProducts", 40L);
        mockProductAnalysis.put("hotProducts", 10L);
        
        List<Map<String, Object>> categoryStats = new ArrayList<>();
        Map<String, Object> category1 = new HashMap<>();
        category1.put("categoryId", 1L);
        category1.put("categoryName", "电子产品");
        category1.put("productCount", 20L);
        categoryStats.add(category1);
        mockProductAnalysis.put("categoryStats", categoryStats);

        mockOrderStatistics = new HashMap<>();
        mockOrderStatistics.put("totalOrders", 100L);
        mockOrderStatistics.put("pendingOrders", 10L);
        mockOrderStatistics.put("completedOrders", 90L);
        mockOrderStatistics.put("monthOrderAmount", 50000.0);
        
        List<Map<String, Object>> customerStats = new ArrayList<>();
        Map<String, Object> customer1 = new HashMap<>();
        customer1.put("customerId", 1L);
        customer1.put("customerName", "测试客户1");
        customer1.put("orderCount", 20L);
        customerStats.add(customer1);
        mockOrderStatistics.put("customerStats", customerStats);

        mockPurchaseStatistics = new HashMap<>();
        mockPurchaseStatistics.put("totalPurchases", 50L);
        mockPurchaseStatistics.put("pendingPurchases", 5L);
        mockPurchaseStatistics.put("completedPurchases", 45L);
        mockPurchaseStatistics.put("monthPurchaseAmount", 30000.0);
        
        List<Map<String, Object>> supplierStats = new ArrayList<>();
        Map<String, Object> supplier1 = new HashMap<>();
        supplier1.put("supplierId", 1L);
        supplier1.put("supplierName", "测试供应商1");
        supplier1.put("purchaseCount", 15L);
        supplierStats.add(supplier1);
        mockPurchaseStatistics.put("supplierStats", supplierStats);

        mockDashboard = new HashMap<>();
        mockDashboard.put("stockStatistics", mockStockStats);
        mockDashboard.put("checkAnalysis", mockCheckAnalysis);
        mockDashboard.put("productAnalysis", mockProductAnalysis);
        mockDashboard.put("orderStatistics", mockOrderStatistics);
        mockDashboard.put("purchaseStatistics", mockPurchaseStatistics);
        mockDashboard.put("alerts", mockAlertList);
    }

    @Test
    public void testGetStockStatistics() {
        System.out.println("=== 开始执行测试: testGetStockStatistics (库存统计报表) ===");
        System.out.println("正在验证库存统计功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getTotalStockQuantity()).thenReturn(1000L);
        when(reportMapper.getTotalStockValue()).thenReturn(100000.0);
        when(reportMapper.getWarehouseCount()).thenReturn(5L);
        when(reportMapper.getMaterialCount()).thenReturn(20L);
        when(reportMapper.getAlertCount()).thenReturn(3L);
        
        List<Map<String, Object>> warehouseStats = new ArrayList<>();
        Map<String, Object> warehouse1 = new HashMap<>();
        warehouse1.put("warehouseId", 1L);
        warehouse1.put("warehouseName", "主仓库");
        warehouse1.put("stockQuantity", 500L);
        warehouseStats.add(warehouse1);
        when(reportMapper.getStockByWarehouse()).thenReturn(warehouseStats);

        // 执行测试方法
        Map<String, Object> result = reportServiceImpl.getStockStatistics();

        // 验证结果
        assertNotNull(result);
        assertEquals(1000L, result.get("totalQuantity"));
        assertEquals(100000.0, result.get("totalValue"));
        assertEquals(5L, result.get("warehouseCount"));
        assertEquals(20L, result.get("materialCount"));
        assertEquals(3L, result.get("alertCount"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getTotalStockQuantity();
        verify(reportMapper, times(1)).getTotalStockValue();
        verify(reportMapper, times(1)).getWarehouseCount();
        verify(reportMapper, times(1)).getMaterialCount();
        verify(reportMapper, times(1)).getAlertCount();
        verify(reportMapper, times(1)).getStockByWarehouse();
        
        System.out.println("✅ 测试通过: 库存统计功能正常");
    }

    @Test
    public void testGetStockAlert() {
        System.out.println("=== 开始执行测试: testGetStockAlert (库存预警报表) ===");
        System.out.println("正在验证库存预警功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getStockAlert()).thenReturn(mockAlertList);

        // 执行测试方法
        List<Map<String, Object>> result = reportServiceImpl.getStockAlert();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试原料1", result.get(0).get("materialName"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getStockAlert();
        
        System.out.println("✅ 测试通过: 库存预警功能正常");
    }

    @Test
    public void testGetCheckAnalysis() {
        System.out.println("=== 开始执行测试: testGetCheckAnalysis (盘点分析报表) ===");
        System.out.println("正在验证盘点分析功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getTotalChecks()).thenReturn(10L);
        when(reportMapper.getOngoingChecks()).thenReturn(2L);
        when(reportMapper.getCompletedChecks()).thenReturn(8L);
        when(reportMapper.getTotalDiscrepancy()).thenReturn(5L);
        
        List<Map<String, Object>> checkDetails = new ArrayList<>();
        Map<String, Object> checkDetail1 = new HashMap<>();
        checkDetail1.put("checkId", 1L);
        checkDetail1.put("checkDate", "2025-11-26");
        checkDetail1.put("discrepancyCount", 2L);
        checkDetails.add(checkDetail1);
        when(reportMapper.getCheckDetails()).thenReturn(checkDetails);

        // 执行测试方法
        Map<String, Object> result = reportServiceImpl.getCheckAnalysis();

        // 验证结果
        assertNotNull(result);
        assertEquals(10L, result.get("totalChecks"));
        assertEquals(2L, result.get("ongoingChecks"));
        assertEquals(8L, result.get("completedChecks"));
        assertEquals(5L, result.get("totalDiscrepancy"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getTotalChecks();
        verify(reportMapper, times(1)).getOngoingChecks();
        verify(reportMapper, times(1)).getCompletedChecks();
        verify(reportMapper, times(1)).getTotalDiscrepancy();
        verify(reportMapper, times(1)).getCheckDetails();
        
        System.out.println("✅ 测试通过: 盘点分析功能正常");
    }

    @Test
    public void testGetMaterialUsage() {
        System.out.println("=== 开始执行测试: testGetMaterialUsage (原料使用分析报表) ===");
        System.out.println("正在验证原料使用分析功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getMaterialUsage()).thenReturn(mockMaterialUsageList);

        // 执行测试方法
        List<Map<String, Object>> result = reportServiceImpl.getMaterialUsage();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试原料1", result.get(0).get("materialName"));
        assertEquals(150L, result.get(0).get("usageCount"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getMaterialUsage();
        
        System.out.println("✅ 测试通过: 原料使用分析功能正常");
    }

    @Test
    public void testGetProductAnalysis() {
        System.out.println("=== 开始执行测试: testGetProductAnalysis (产品分析报表) ===");
        System.out.println("正在验证产品分析功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getTotalProducts()).thenReturn(50L);
        when(reportMapper.getOnSaleProducts()).thenReturn(40L);
        when(reportMapper.getHotProducts()).thenReturn(10L);
        
        List<Map<String, Object>> categoryStats = new ArrayList<>();
        Map<String, Object> category1 = new HashMap<>();
        category1.put("categoryId", 1L);
        category1.put("categoryName", "电子产品");
        category1.put("productCount", 20L);
        categoryStats.add(category1);
        when(reportMapper.getProductByCategory()).thenReturn(categoryStats);

        // 执行测试方法
        Map<String, Object> result = reportServiceImpl.getProductAnalysis();

        // 验证结果
        assertNotNull(result);
        assertEquals(50L, result.get("totalProducts"));
        assertEquals(40L, result.get("onSaleProducts"));
        assertEquals(10L, result.get("hotProducts"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getTotalProducts();
        verify(reportMapper, times(1)).getOnSaleProducts();
        verify(reportMapper, times(1)).getHotProducts();
        verify(reportMapper, times(1)).getProductByCategory();
        
        System.out.println("✅ 测试通过: 产品分析功能正常");
    }

    @Test
    public void testGetOrderStatistics() {
        System.out.println("=== 开始执行测试: testGetOrderStatistics (订单统计报表) ===");
        System.out.println("正在验证订单统计功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getTotalOrders()).thenReturn(100L);
        when(reportMapper.getPendingOrders()).thenReturn(10L);
        when(reportMapper.getCompletedOrders()).thenReturn(90L);
        when(reportMapper.getMonthOrderAmount()).thenReturn(50000.0);
        
        List<Map<String, Object>> customerStats = new ArrayList<>();
        Map<String, Object> customer1 = new HashMap<>();
        customer1.put("customerId", 1L);
        customer1.put("customerName", "测试客户1");
        customer1.put("orderCount", 20L);
        customerStats.add(customer1);
        when(reportMapper.getOrderByCustomer()).thenReturn(customerStats);

        // 执行测试方法
        Map<String, Object> result = reportServiceImpl.getOrderStatistics();

        // 验证结果
        assertNotNull(result);
        assertEquals(100L, result.get("totalOrders"));
        assertEquals(10L, result.get("pendingOrders"));
        assertEquals(90L, result.get("completedOrders"));
        assertEquals(50000.0, result.get("monthOrderAmount"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getTotalOrders();
        verify(reportMapper, times(1)).getPendingOrders();
        verify(reportMapper, times(1)).getCompletedOrders();
        verify(reportMapper, times(1)).getMonthOrderAmount();
        verify(reportMapper, times(1)).getOrderByCustomer();
        
        System.out.println("✅ 测试通过: 订单统计功能正常");
    }

    @Test
    public void testGetPurchaseStatistics() {
        System.out.println("=== 开始执行测试: testGetPurchaseStatistics (采购统计报表) ===");
        System.out.println("正在验证采购统计功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getTotalPurchases()).thenReturn(50L);
        when(reportMapper.getPendingPurchases()).thenReturn(5L);
        when(reportMapper.getCompletedPurchases()).thenReturn(45L);
        when(reportMapper.getMonthPurchaseAmount()).thenReturn(30000.0);
        
        List<Map<String, Object>> supplierStats = new ArrayList<>();
        Map<String, Object> supplier1 = new HashMap<>();
        supplier1.put("supplierId", 1L);
        supplier1.put("supplierName", "测试供应商1");
        supplier1.put("purchaseCount", 15L);
        supplierStats.add(supplier1);
        when(reportMapper.getPurchaseBySupplier()).thenReturn(supplierStats);

        // 执行测试方法
        Map<String, Object> result = reportServiceImpl.getPurchaseStatistics();

        // 验证结果
        assertNotNull(result);
        assertEquals(50L, result.get("totalPurchases"));
        assertEquals(5L, result.get("pendingPurchases"));
        assertEquals(45L, result.get("completedPurchases"));
        assertEquals(30000.0, result.get("monthPurchaseAmount"));
        
        // 验证Mapper方法是否被调用
        verify(reportMapper, times(1)).getTotalPurchases();
        verify(reportMapper, times(1)).getPendingPurchases();
        verify(reportMapper, times(1)).getCompletedPurchases();
        verify(reportMapper, times(1)).getMonthPurchaseAmount();
        verify(reportMapper, times(1)).getPurchaseBySupplier();
        
        System.out.println("✅ 测试通过: 采购统计功能正常");
    }

    @Test
    public void testGetDashboard() {
        System.out.println("=== 开始执行测试: testGetDashboard (综合仪表盘数据) ===");
        System.out.println("正在验证综合仪表盘功能...");
        
        // 模拟Mapper调用
        when(reportMapper.getTotalStockQuantity()).thenReturn(1000L);
        when(reportMapper.getTotalStockValue()).thenReturn(100000.0);
        when(reportMapper.getWarehouseCount()).thenReturn(5L);
        when(reportMapper.getMaterialCount()).thenReturn(20L);
        when(reportMapper.getAlertCount()).thenReturn(3L);
        when(reportMapper.getStockByWarehouse()).thenReturn(new ArrayList<>());
        when(reportMapper.getStockAlert()).thenReturn(mockAlertList);
        when(reportMapper.getTotalChecks()).thenReturn(10L);
        when(reportMapper.getOngoingChecks()).thenReturn(2L);
        when(reportMapper.getCompletedChecks()).thenReturn(8L);
        when(reportMapper.getTotalDiscrepancy()).thenReturn(5L);
        when(reportMapper.getCheckDetails()).thenReturn(new ArrayList<>());
        when(reportMapper.getTotalProducts()).thenReturn(50L);
        when(reportMapper.getOnSaleProducts()).thenReturn(40L);
        when(reportMapper.getHotProducts()).thenReturn(10L);
        when(reportMapper.getProductByCategory()).thenReturn(new ArrayList<>());
        when(reportMapper.getTotalOrders()).thenReturn(100L);
        when(reportMapper.getPendingOrders()).thenReturn(10L);
        when(reportMapper.getCompletedOrders()).thenReturn(90L);
        when(reportMapper.getMonthOrderAmount()).thenReturn(50000.0);
        when(reportMapper.getOrderByCustomer()).thenReturn(new ArrayList<>());
        when(reportMapper.getTotalPurchases()).thenReturn(50L);
        when(reportMapper.getPendingPurchases()).thenReturn(5L);
        when(reportMapper.getCompletedPurchases()).thenReturn(45L);
        when(reportMapper.getMonthPurchaseAmount()).thenReturn(30000.0);
        when(reportMapper.getPurchaseBySupplier()).thenReturn(new ArrayList<>());

        // 执行测试方法
        Map<String, Object> result = reportServiceImpl.getDashboard();

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("stockStatistics"));
        assertTrue(result.containsKey("checkAnalysis"));
        assertTrue(result.containsKey("productAnalysis"));
        assertTrue(result.containsKey("orderStatistics"));
        assertTrue(result.containsKey("purchaseStatistics"));
        assertTrue(result.containsKey("alerts"));
        
        System.out.println("✅ 测试通过: 综合仪表盘功能正常");
    }
}