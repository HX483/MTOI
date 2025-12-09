package com.ruoyi.purchase.service.impl;

import com.ruoyi.purchase.domain.PurchaseDetail;
import com.ruoyi.purchase.mapper.PurchaseDetailMapper;
import com.ruoyi.purchase.service.IPurchaseDetailService;
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

@ExtendWith(MockitoExtension.class)
public class PurchaseDetailServiceImplTest {

    @Mock
    private PurchaseDetailMapper purchaseDetailMapper;

    @InjectMocks
    private PurchaseDetailServiceImpl purchaseDetailService;

    private PurchaseDetail purchaseDetail;

    @BeforeEach
    public void setUp() {
        // 初始化采购明细对象
        purchaseDetail = new PurchaseDetail();
        purchaseDetail.setDetailId(1L);
        purchaseDetail.setPurchaseId(1L);
        purchaseDetail.setMaterialId(1L);
        purchaseDetail.setMaterialName("原料1");
        purchaseDetail.setSpecification("规格1");
        purchaseDetail.setUnit("kg");
        purchaseDetail.setQuantity(new BigDecimal(10));
        purchaseDetail.setUnitPrice(new BigDecimal(50));
        purchaseDetail.setAmount(new BigDecimal(500));
        purchaseDetail.setActualQuantity(new BigDecimal(10));
    }

    /**
     * 测试目标：测试采购明细查询功能
     * 输入参数：采购明细ID
     * 预期输出：返回对应的采购明细信息
     * 断言验证：采购明细ID和原料名称是否正确
     */
    @Test
    public void testSelectPurchaseDetailByDetailId() {
        System.out.println("=== 开始执行测试: testSelectPurchaseDetailByDetailId (查询采购明细详情) ===");
        // 准备数据
        when(purchaseDetailMapper.selectPurchaseDetailByDetailId(1L)).thenReturn(purchaseDetail);

        // 执行方法
        PurchaseDetail result = purchaseDetailService.selectPurchaseDetailByDetailId(1L);

        // 验证结果
        System.out.println("正在验证采购明细详情查询结果...");
        assertNotNull(result, "采购明细信息不应该为null");
        assertEquals(1L, result.getDetailId(), "采购明细ID应该为1");
        assertEquals("原料1", result.getMaterialName(), "原料名称应该为'原料1'");
        verify(purchaseDetailMapper, times(1)).selectPurchaseDetailByDetailId(1L);
        System.out.println("✅ 测试通过: 成功查询到采购明细详情");
    }

    /**
     * 测试目标：测试采购明细列表查询功能
     * 输入参数：采购明细查询条件
     * 预期输出：返回符合条件的采购明细列表
     * 断言验证：返回的采购明细列表是否包含预期的采购明细
     */
    @Test
    public void testSelectPurchaseDetailList() {
        System.out.println("=== 开始执行测试: testSelectPurchaseDetailList (查询采购明细列表) ===");
        // 准备数据
        List<PurchaseDetail> purchaseDetailList = new ArrayList<>();
        purchaseDetailList.add(purchaseDetail);
        when(purchaseDetailMapper.selectPurchaseDetailList(purchaseDetail)).thenReturn(purchaseDetailList);

        // 执行方法
        List<PurchaseDetail> result = purchaseDetailService.selectPurchaseDetailList(purchaseDetail);

        // 验证结果
        System.out.println("正在验证采购明细列表查询结果...");
        assertNotNull(result, "采购明细列表不应该为null");
        assertFalse(result.isEmpty(), "采购明细列表不应该为空");
        assertEquals(1, result.size(), "采购明细列表应该包含1条记录");
        verify(purchaseDetailMapper, times(1)).selectPurchaseDetailList(purchaseDetail);
        System.out.println("✅ 测试通过: 成功查询到采购明细列表");
    }

    /**
     * 测试目标：测试采购明细新增功能
     * 输入参数：采购明细信息
     * 预期输出：返回新增成功的记录数
     * 断言验证：采购明细新增方法是否被调用
     */
    @Test
    public void testInsertPurchaseDetail() {
        System.out.println("=== 开始执行测试: testInsertPurchaseDetail (新增采购明细) ===");
        // 准备数据
        when(purchaseDetailMapper.insertPurchaseDetail(purchaseDetail)).thenReturn(1);

        // 执行方法
        int result = purchaseDetailService.insertPurchaseDetail(purchaseDetail);

        // 验证结果
        System.out.println("正在验证采购明细新增结果...");
        assertEquals(1, result, "新增采购明细应该返回1");
        verify(purchaseDetailMapper, times(1)).insertPurchaseDetail(purchaseDetail);
        System.out.println("✅ 测试通过: 成功新增采购明细");
    }

    /**
     * 测试目标：测试采购明细修改功能
     * 输入参数：包含更新信息的采购明细
     * 预期输出：返回修改成功的记录数
     * 断言验证：采购明细修改方法是否被调用
     */
    @Test
    public void testUpdatePurchaseDetail() {
        System.out.println("=== 开始执行测试: testUpdatePurchaseDetail (修改采购明细) ===");
        // 准备数据
        purchaseDetail.setMaterialName("更新后的原料名称");
        when(purchaseDetailMapper.updatePurchaseDetail(purchaseDetail)).thenReturn(1);

        // 执行方法
        int result = purchaseDetailService.updatePurchaseDetail(purchaseDetail);

        // 验证结果
        System.out.println("正在验证采购明细修改结果...");
        assertEquals(1, result, "修改采购明细应该返回1");
        verify(purchaseDetailMapper, times(1)).updatePurchaseDetail(purchaseDetail);
        System.out.println("✅ 测试通过: 成功修改采购明细信息");
    }

    /**
     * 测试目标：测试单个采购明细删除功能
     * 输入参数：采购明细ID
     * 预期输出：返回删除成功的记录数
     * 断言验证：采购明细删除方法是否被调用
     */
    @Test
    public void testDeletePurchaseDetailByDetailId() {
        System.out.println("=== 开始执行测试: testDeletePurchaseDetailByDetailId (删除单个采购明细) ===");
        // 准备数据
        when(purchaseDetailMapper.deletePurchaseDetailByDetailId(1L)).thenReturn(1);

        // 执行方法
        int result = purchaseDetailService.deletePurchaseDetailByDetailId(1L);

        // 验证结果
        System.out.println("正在验证采购明细删除结果...");
        assertEquals(1, result, "删除采购明细应该返回1");
        verify(purchaseDetailMapper, times(1)).deletePurchaseDetailByDetailId(1L);
        System.out.println("✅ 测试通过: 成功删除单个采购明细");
    }

    /**
     * 测试目标：测试批量采购明细删除功能
     * 输入参数：采购明细ID数组
     * 预期输出：返回删除成功的记录数
     * 断言验证：批量删除方法是否被调用
     */
    @Test
    public void testDeletePurchaseDetailByDetailIds() {
        System.out.println("=== 开始执行测试: testDeletePurchaseDetailByDetailIds (批量删除采购明细) ===");
        // 准备数据
        Long[] detailIds = {1L, 2L};
        when(purchaseDetailMapper.deletePurchaseDetailByDetailIds(detailIds)).thenReturn(2);

        // 执行方法
        int result = purchaseDetailService.deletePurchaseDetailByDetailIds(detailIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(2, result, "批量删除采购明细应该返回2");
        verify(purchaseDetailMapper, times(1)).deletePurchaseDetailByDetailIds(detailIds);
        System.out.println("✅ 测试通过: 成功批量删除采购明细");
    }
}