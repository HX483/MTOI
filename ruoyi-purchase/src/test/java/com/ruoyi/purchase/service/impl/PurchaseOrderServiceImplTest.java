package com.ruoyi.purchase.service.impl;

import com.ruoyi.purchase.domain.PurchaseOrder;
import com.ruoyi.purchase.domain.PurchaseDetail;
import com.ruoyi.purchase.mapper.PurchaseOrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 采购订单服务层实现类单元测试
 * 
 * @author xihao
 * @date 2025-11-30
 */
@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceImplTest {

    @Mock
    private PurchaseOrderMapper purchaseOrderMapper;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    private PurchaseOrder purchaseOrder;
    private List<PurchaseDetail> purchaseDetailList;

    @BeforeEach
    public void setUp() {
        // 初始化采购订单对象
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseId(1L);
        purchaseOrder.setPurchaseNo("PO-2025-0001");
        purchaseOrder.setSupplierId(1L);
        purchaseOrder.setTotalAmount(new BigDecimal(1000));
        purchaseOrder.setStatus(1);

        // 初始化采购明细列表
        purchaseDetailList = new ArrayList<>();
        PurchaseDetail detail1 = new PurchaseDetail();
        detail1.setDetailId(1L);
        detail1.setPurchaseId(1L);
        detail1.setMaterialId(1L);
        detail1.setMaterialName("原料1");
        detail1.setQuantity(new BigDecimal(10));
        detail1.setUnitPrice(new BigDecimal(50));
        detail1.setAmount(new BigDecimal(500));
        purchaseDetailList.add(detail1);

        PurchaseDetail detail2 = new PurchaseDetail();
        detail2.setDetailId(2L);
        detail2.setPurchaseId(1L);
        detail2.setMaterialId(2L);
        detail2.setMaterialName("原料2");
        detail2.setQuantity(new BigDecimal(5));
        detail2.setUnitPrice(new BigDecimal(100));
        detail2.setAmount(new BigDecimal(500));
        purchaseDetailList.add(detail2);

        purchaseOrder.setPurchaseDetailList(purchaseDetailList);
    }

    /**
     * 测试目标：测试采购订单查询功能
     * 输入参数：采购订单ID
     * 预期输出：返回对应的采购订单信息
     * 断言验证：采购订单ID和采购订单号是否正确
     */
    @Test
    public void testSelectPurchaseOrderByPurchaseId() {
        System.out.println("=== 开始执行测试: testSelectPurchaseOrderByPurchaseId (查询采购订单详情) ===");
        // 准备数据
        when(purchaseOrderMapper.selectPurchaseOrderByPurchaseId(1L)).thenReturn(purchaseOrder);

        // 执行方法
        PurchaseOrder result = purchaseOrderService.selectPurchaseOrderByPurchaseId(1L);

        // 验证结果
        System.out.println("正在验证采购订单详情查询结果...");
        assertNotNull(result, "采购订单信息不应该为null");
        assertEquals(1L, result.getPurchaseId(), "采购订单ID应该为1");
        assertEquals("PO-2025-0001", result.getPurchaseNo(), "采购订单号应该为PO-2025-0001");
        verify(purchaseOrderMapper, times(1)).selectPurchaseOrderByPurchaseId(1L);
        System.out.println("✅ 测试通过: 成功查询到采购订单详情");
    }

    /**
     * 测试目标：测试采购订单列表查询功能
     * 输入参数：采购订单查询条件
     * 预期输出：返回符合条件的采购订单列表
     * 断言验证：返回的采购订单列表是否包含预期的采购订单
     */
    @Test
    public void testSelectPurchaseOrderList() {
        System.out.println("=== 开始执行测试: testSelectPurchaseOrderList (查询采购订单列表) ===");
        // 准备数据
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        purchaseOrderList.add(purchaseOrder);
        when(purchaseOrderMapper.selectPurchaseOrderList(purchaseOrder)).thenReturn(purchaseOrderList);

        // 执行方法
        List<PurchaseOrder> result = purchaseOrderService.selectPurchaseOrderList(purchaseOrder);

        // 验证结果
        System.out.println("正在验证采购订单列表查询结果...");
        assertNotNull(result, "采购订单列表不应该为null");
        assertFalse(result.isEmpty(), "采购订单列表不应该为空");
        assertEquals(1, result.size(), "采购订单列表应该包含1条记录");
        verify(purchaseOrderMapper, times(1)).selectPurchaseOrderList(purchaseOrder);
        System.out.println("✅ 测试通过: 成功查询到采购订单列表");
    }

    /**
     * 测试目标：测试采购订单新增功能
     * 输入参数：包含采购明细的采购订单信息
     * 预期输出：返回新增成功的记录数
     * 断言验证：采购订单新增方法和采购明细批量新增方法是否被调用
     */
    @Test
    public void testInsertPurchaseOrder() {
        System.out.println("=== 开始执行测试: testInsertPurchaseOrder (新增采购订单) ===");
        // 准备数据
        when(purchaseOrderMapper.insertPurchaseOrder(purchaseOrder)).thenReturn(1);
        when(purchaseOrderMapper.batchPurchaseDetail(purchaseDetailList)).thenReturn(2);

        // 执行方法
        int result = purchaseOrderService.insertPurchaseOrder(purchaseOrder);

        // 验证结果
        System.out.println("正在验证采购订单新增结果...");
        assertEquals(1, result, "新增采购订单应该返回1");
        verify(purchaseOrderMapper, times(1)).insertPurchaseOrder(purchaseOrder);
        verify(purchaseOrderMapper, times(1)).batchPurchaseDetail(purchaseDetailList);
        System.out.println("✅ 测试通过: 成功新增采购订单");
    }

    /**
     * 测试目标：测试采购订单修改功能
     * 输入参数：包含更新信息的采购订单
     * 预期输出：返回修改成功的记录数
     * 断言验证：采购订单修改方法和采购明细更新方法是否被调用
     */
    @Test
    public void testUpdatePurchaseOrder() {
        System.out.println("=== 开始执行测试: testUpdatePurchaseOrder (修改采购订单) ===");
        // 准备数据
        when(purchaseOrderMapper.updatePurchaseOrder(purchaseOrder)).thenReturn(1);
        when(purchaseOrderMapper.deletePurchaseDetailByPurchaseId(1L)).thenReturn(2);
        when(purchaseOrderMapper.batchPurchaseDetail(purchaseDetailList)).thenReturn(2);

        // 执行方法
        int result = purchaseOrderService.updatePurchaseOrder(purchaseOrder);

        // 验证结果
        System.out.println("正在验证采购订单修改结果...");
        assertEquals(1, result, "修改采购订单应该返回1");
        verify(purchaseOrderMapper, times(1)).updatePurchaseOrder(purchaseOrder);
        verify(purchaseOrderMapper, times(1)).deletePurchaseDetailByPurchaseId(1L);
        verify(purchaseOrderMapper, times(1)).batchPurchaseDetail(purchaseDetailList);
        System.out.println("✅ 测试通过: 成功修改采购订单信息");
    }

    /**
     * 测试目标：测试单个采购订单删除功能
     * 输入参数：采购订单ID
     * 预期输出：返回删除成功的记录数
     * 断言验证：采购订单删除方法和采购明细删除方法是否被调用
     */
    @Test
    public void testDeletePurchaseOrderByPurchaseId() {
        System.out.println("=== 开始执行测试: testDeletePurchaseOrderByPurchaseId (删除单个采购订单) ===");
        // 准备数据
        when(purchaseOrderMapper.deletePurchaseOrderByPurchaseId(1L)).thenReturn(1);
        when(purchaseOrderMapper.deletePurchaseDetailByPurchaseId(1L)).thenReturn(2);

        // 执行方法
        int result = purchaseOrderService.deletePurchaseOrderByPurchaseId(1L);

        // 验证结果
        System.out.println("正在验证采购订单删除结果...");
        assertEquals(1, result, "删除采购订单应该返回1");
        verify(purchaseOrderMapper, times(1)).deletePurchaseOrderByPurchaseId(1L);
        verify(purchaseOrderMapper, times(1)).deletePurchaseDetailByPurchaseId(1L);
        System.out.println("✅ 测试通过: 成功删除单个采购订单");
    }

    /**
     * 测试目标：测试批量采购订单删除功能
     * 输入参数：采购订单ID数组
     * 预期输出：返回删除成功的记录数
     * 断言验证：批量删除方法是否被调用
     */
    @Test
    public void testDeletePurchaseOrderByPurchaseIds() {
        System.out.println("=== 开始执行测试: testDeletePurchaseOrderByPurchaseIds (批量删除采购订单) ===");
        // 准备数据
        Long[] purchaseIds = {1L, 2L};
        when(purchaseOrderMapper.deletePurchaseOrderByPurchaseIds(purchaseIds)).thenReturn(2);
        when(purchaseOrderMapper.deletePurchaseDetailByPurchaseIds(purchaseIds)).thenReturn(4);

        // 执行方法
        int result = purchaseOrderService.deletePurchaseOrderByPurchaseIds(purchaseIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(2, result, "批量删除采购订单应该返回2");
        verify(purchaseOrderMapper, times(1)).deletePurchaseOrderByPurchaseIds(purchaseIds);
        verify(purchaseOrderMapper, times(1)).deletePurchaseDetailByPurchaseIds(purchaseIds);
        System.out.println("✅ 测试通过: 成功批量删除采购订单");
    }
}