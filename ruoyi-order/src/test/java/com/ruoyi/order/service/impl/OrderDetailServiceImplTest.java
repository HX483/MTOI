package com.ruoyi.order.service.impl;

import com.ruoyi.order.domain.OrderDetail;
import com.ruoyi.order.mapper.OrderDetailMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 订单明细服务层实现类单元测试
 *
 * @author xihao
 * @date 2025-11-30
 */
@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceImplTest {

    @Mock
    private OrderDetailMapper orderDetailMapper;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    private OrderDetail orderDetail;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        orderDetail = new OrderDetail();
        orderDetail.setDetailId(1L);
        orderDetail.setOrderId(1L);
        orderDetail.setProductId(1L);
        orderDetail.setProductName("珍珠奶茶");
        orderDetail.setQuantity(2L);
        orderDetail.setPrice(new BigDecimal(15.00));
        orderDetail.setSubtotal(new BigDecimal(30.00));
        orderDetail.setCustomization("少糖、去冰");
    }

    /**
     * 测试目标：测试根据ID查询订单明细
     * 输入参数：明细ID
     * 预期输出：返回对应的订单明细
     * 断言验证：明细ID和产品名称是否正确
     */
    @Test
    public void testSelectOrderDetailByDetailId() {
        System.out.println("=== 开始执行测试: testSelectOrderDetailByDetailId (根据ID查询订单明细) ===");
        // 模拟Mapper行为
        when(orderDetailMapper.selectOrderDetailByDetailId(1L)).thenReturn(orderDetail);

        // 执行测试
        OrderDetail result = orderDetailService.selectOrderDetailByDetailId(1L);

        // 验证结果
        System.out.println("正在验证查询结果...");
        assertNotNull(result);
        assertEquals(1L, result.getDetailId());
        assertEquals("珍珠奶茶", result.getProductName());
        verify(orderDetailMapper, times(1)).selectOrderDetailByDetailId(1L);
        System.out.println("✅ 测试通过: 成功根据ID查询到订单明细");
    }

    /**
     * 测试目标：测试查询订单明细列表
     * 输入参数：订单明细查询条件
     * 预期输出：返回符合条件的订单明细列表
     * 断言验证：列表是否包含预期的明细项
     */
    @Test
    public void testSelectOrderDetailList() {
        System.out.println("=== 开始执行测试: testSelectOrderDetailList (查询订单明细列表) ===");
        // 模拟数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        // 模拟Mapper行为
        when(orderDetailMapper.selectOrderDetailList(any(OrderDetail.class))).thenReturn(orderDetailList);

        // 执行测试
        List<OrderDetail> result = orderDetailService.selectOrderDetailList(new OrderDetail());

        // 验证结果
        System.out.println("正在验证列表查询结果...");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("珍珠奶茶", result.get(0).getProductName());
        verify(orderDetailMapper, times(1)).selectOrderDetailList(any(OrderDetail.class));
        System.out.println("✅ 测试通过: 成功查询到订单明细列表");
    }

    /**
     * 测试目标：测试新增订单明细
     * 输入参数：订单明细对象
     * 预期输出：返回新增结果
     * 断言验证：是否成功新增，并检查创建时间是否自动设置
     */
    @Test
    public void testInsertOrderDetail() {
        System.out.println("=== 开始执行测试: testInsertOrderDetail (新增订单明细) ===");
        // 模拟Mapper行为
        when(orderDetailMapper.insertOrderDetail(any(OrderDetail.class))).thenReturn(1);

        // 执行测试
        int result = orderDetailService.insertOrderDetail(orderDetail);

        // 验证结果
        System.out.println("正在验证新增结果...");
        assertEquals(1, result);
        assertNotNull(orderDetail.getCreateTime());
        verify(orderDetailMapper, times(1)).insertOrderDetail(any(OrderDetail.class));
        System.out.println("✅ 测试通过: 成功新增订单明细");
    }

    /**
     * 测试目标：测试修改订单明细
     * 输入参数：更新后的订单明细对象
     * 预期输出：返回修改结果
     * 断言验证：是否成功修改
     */
    @Test
    public void testUpdateOrderDetail() {
        System.out.println("=== 开始执行测试: testUpdateOrderDetail (修改订单明细) ===");
        // 修改明细信息
        orderDetail.setProductName("草莓奶茶");

        // 模拟Mapper行为
        when(orderDetailMapper.updateOrderDetail(any(OrderDetail.class))).thenReturn(1);

        // 执行测试
        int result = orderDetailService.updateOrderDetail(orderDetail);

        // 验证结果
        System.out.println("正在验证修改结果...");
        assertEquals(1, result);
        assertEquals("草莓奶茶", orderDetail.getProductName());
        verify(orderDetailMapper, times(1)).updateOrderDetail(any(OrderDetail.class));
        System.out.println("✅ 测试通过: 成功修改订单明细");
    }

    /**
     * 测试目标：测试删除单个订单明细
     * 输入参数：明细ID
     * 预期输出：返回删除结果
     * 断言验证：是否成功删除
     */
    @Test
    public void testDeleteOrderDetailByDetailId() {
        System.out.println("=== 开始执行测试: testDeleteOrderDetailByDetailId (删除单个订单明细) ===");
        // 模拟Mapper行为
        when(orderDetailMapper.deleteOrderDetailByDetailId(1L)).thenReturn(1);

        // 执行测试
        int result = orderDetailService.deleteOrderDetailByDetailId(1L);

        // 验证结果
        System.out.println("正在验证删除结果...");
        assertEquals(1, result);
        verify(orderDetailMapper, times(1)).deleteOrderDetailByDetailId(1L);
        System.out.println("✅ 测试通过: 成功删除单个订单明细");
    }

    /**
     * 测试目标：测试批量删除订单明细
     * 输入参数：明细ID数组
     * 预期输出：返回删除结果
     * 断言验证：是否成功批量删除
     */
    @Test
    public void testDeleteOrderDetailByDetailIds() {
        System.out.println("=== 开始执行测试: testDeleteOrderDetailByDetailIds (批量删除订单明细) ===");
        // 准备测试数据
        Long[] detailIds = {1L, 2L, 3L};

        // 模拟Mapper行为
        when(orderDetailMapper.deleteOrderDetailByDetailIds(detailIds)).thenReturn(3);

        // 执行测试
        int result = orderDetailService.deleteOrderDetailByDetailIds(detailIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(3, result);
        verify(orderDetailMapper, times(1)).deleteOrderDetailByDetailIds(detailIds);
        System.out.println("✅ 测试通过: 成功批量删除订单明细");
    }
}
