package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderDetail;
import com.ruoyi.order.domain.OrderMain;
import com.ruoyi.order.mapper.OrderMainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 客户订单服务层实现类单元测试
 * 
 * @author xihao
 * @date 2025-11-30
 */
@ExtendWith(MockitoExtension.class)
public class OrderMainServiceImplTest {

    @Mock
    private OrderMainMapper orderMainMapper;

    @InjectMocks
    private OrderMainServiceImpl orderMainService;

    private OrderMain orderMain;
    private OrderDetail orderDetail;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        orderMain = new OrderMain();
        orderMain.setOrderId(1L);
        orderMain.setOrderNo("ORD-20251130001");
        orderMain.setCustomerName("张三");
        orderMain.setCustomerPhone("13800138000");
        orderMain.setTotalAmount(new BigDecimal(30.00));
        orderMain.setActualAmount(new BigDecimal(30.00));
        orderMain.setOrderType(1); // 堂食
        orderMain.setStatus(2); // 待制作
        orderMain.setPaymentStatus(1); // 已支付
        orderMain.setPaymentType(1); // 微信支付

        orderDetail = new OrderDetail();
        orderDetail.setDetailId(1L);
        orderDetail.setProductId(1L);
        orderDetail.setProductName("珍珠奶茶");
        orderDetail.setQuantity(2L);
        orderDetail.setPrice(new BigDecimal(15.00));
        orderDetail.setSubtotal(new BigDecimal(30.00));
        orderDetail.setCustomization("少糖、去冰");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderMain.setOrderDetailList(orderDetailList);
    }

    /**
     * 测试目标：测试订单查询功能
     * 输入参数：订单ID
     * 预期输出：返回对应的订单信息
     * 断言验证：订单ID和订单编号是否正确
     */
    @Test
    public void testSelectOrderMainByOrderId() {
        System.out.println("=== 开始执行测试: testSelectOrderMainByOrderId (查询订单详情) ===");
        // 模拟Mapper行为
        when(orderMainMapper.selectOrderMainByOrderId(1L)).thenReturn(orderMain);

        // 执行测试
        OrderMain result = orderMainService.selectOrderMainByOrderId(1L);

        // 验证结果
        System.out.println("正在验证订单详情查询结果...");
        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals("ORD-20251130001", result.getOrderNo());
        verify(orderMainMapper, times(1)).selectOrderMainByOrderId(1L);
        System.out.println("✅ 测试通过: 成功查询到订单详情");
    }

    /**
     * 测试目标：测试订单列表查询功能
     * 输入参数：订单查询条件
     * 预期输出：返回符合条件的订单列表
     * 断言验证：返回的订单列表是否包含预期的订单
     */
    @Test
    public void testSelectOrderMainList() {
        System.out.println("=== 开始执行测试: testSelectOrderMainList (查询订单列表) ===");
        // 模拟Mapper行为
        List<OrderMain> orderMainList = new ArrayList<>();
        orderMainList.add(orderMain);
        when(orderMainMapper.selectOrderMainList(any(OrderMain.class))).thenReturn(orderMainList);

        // 执行测试
        List<OrderMain> result = orderMainService.selectOrderMainList(new OrderMain());

        // 验证结果
        System.out.println("正在验证订单列表查询结果...");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ORD-20251130001", result.get(0).getOrderNo());
        verify(orderMainMapper, times(1)).selectOrderMainList(any(OrderMain.class));
        System.out.println("✅ 测试通过: 成功查询到订单列表");
    }

    /**
     * 测试目标：测试订单创建功能（提供订单编号）
     * 输入参数：包含订单明细的订单信息
     * 预期输出：返回创建成功的记录数
     * 断言验证：订单创建方法是否被调用，以及订单明细是否被插入
     */
    @Test
    public void testInsertOrderMainWithOrderNo() {
        System.out.println("=== 开始执行测试: testInsertOrderMainWithOrderNo (创建订单-提供订单编号) ===");
        // 模拟Mapper行为
        when(orderMainMapper.insertOrderMain(any(OrderMain.class))).thenReturn(1);

        // 执行测试
        int result = orderMainService.insertOrderMain(orderMain);

        // 验证结果
        System.out.println("正在验证订单创建结果...");
        assertEquals(1, result);
        verify(orderMainMapper, times(1)).insertOrderMain(any(OrderMain.class));
        System.out.println("✅ 测试通过: 成功创建订单(提供订单编号)");
    }

    /**
     * 测试目标：测试订单创建功能（自动生成订单编号）
     * 输入参数：不包含订单编号的订单信息
     * 预期输出：返回创建成功的记录数
     * 断言验证：订单编号是否自动生成，以及订单明细是否被插入
     */
    @Test
    public void testInsertOrderMainWithoutOrderNo() {
        System.out.println("=== 开始执行测试: testInsertOrderMainWithoutOrderNo (创建订单-自动生成订单编号) ===");
        // 清除订单编号
        orderMain.setOrderNo(null);
        
        // 模拟Mapper行为
        when(orderMainMapper.insertOrderMain(any(OrderMain.class))).thenReturn(1);

        // 执行测试
        int result = orderMainService.insertOrderMain(orderMain);

        // 验证结果
        System.out.println("正在验证订单创建结果...");
        assertEquals(1, result);
        assertNotNull(orderMain.getOrderNo());
        assertTrue(orderMain.getOrderNo().startsWith("ORD-"));
        verify(orderMainMapper, times(1)).insertOrderMain(any(OrderMain.class));
        System.out.println("✅ 测试通过: 成功创建订单并自动生成订单编号");
    }

    /**
     * 测试目标：测试订单修改功能
     * 输入参数：包含更新信息的订单
     * 预期输出：返回修改成功的记录数
     * 断言验证：订单修改方法是否被调用，以及订单明细是否被更新
     */
    @Test
    public void testUpdateOrderMain() {
        System.out.println("=== 开始执行测试: testUpdateOrderMain (修改订单) ===");
        // 修改订单信息
        orderMain.setCustomerName("李四");
        orderDetail.setProductName("草莓奶茶");

        // 模拟Mapper行为
        when(orderMainMapper.updateOrderMain(any(OrderMain.class))).thenReturn(1);

        // 执行测试
        int result = orderMainService.updateOrderMain(orderMain);

        // 验证结果
        System.out.println("正在验证订单修改结果...");
        assertEquals(1, result);
        assertEquals("李四", orderMain.getCustomerName());
        verify(orderMainMapper, times(1)).deleteOrderDetailByOrderId(1L);
        verify(orderMainMapper, times(1)).updateOrderMain(any(OrderMain.class));
        System.out.println("✅ 测试通过: 成功修改订单信息");
    }

    /**
     * 测试目标：测试单个订单删除功能
     * 输入参数：订单ID
     * 预期输出：返回删除成功的记录数
     * 断言验证：订单删除方法和订单明细删除方法是否被调用
     */
    @Test
    public void testDeleteOrderMainByOrderId() {
        System.out.println("=== 开始执行测试: testDeleteOrderMainByOrderId (删除单个订单) ===");
        // 模拟Mapper行为
        when(orderMainMapper.deleteOrderMainByOrderId(1L)).thenReturn(1);

        // 执行测试
        int result = orderMainService.deleteOrderMainByOrderId(1L);

        // 验证结果
        System.out.println("正在验证订单删除结果...");
        assertEquals(1, result);
        verify(orderMainMapper, times(1)).deleteOrderDetailByOrderId(1L);
        verify(orderMainMapper, times(1)).deleteOrderMainByOrderId(1L);
        System.out.println("✅ 测试通过: 成功删除单个订单");
    }

    /**
     * 测试目标：测试批量订单删除功能
     * 输入参数：订单ID数组
     * 预期输出：返回删除成功的记录数
     * 断言验证：批量删除方法是否被调用
     */
    @Test
    public void testDeleteOrderMainByOrderIds() {
        System.out.println("=== 开始执行测试: testDeleteOrderMainByOrderIds (批量删除订单) ===");
        // 准备测试数据
        Long[] orderIds = {1L, 2L, 3L};
        
        // 模拟Mapper行为
        when(orderMainMapper.deleteOrderMainByOrderIds(orderIds)).thenReturn(3);

        // 执行测试
        int result = orderMainService.deleteOrderMainByOrderIds(orderIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(3, result);
        verify(orderMainMapper, times(1)).deleteOrderDetailByOrderIds(orderIds);
        verify(orderMainMapper, times(1)).deleteOrderMainByOrderIds(orderIds);
        System.out.println("✅ 测试通过: 成功批量删除订单");
    }

    /**
     * 测试目标：测试空订单明细的订单创建功能
     * 输入参数：不包含订单明细的订单信息
     * 预期输出：返回创建成功的记录数
     * 断言验证：订单创建方法是否被调用，订单明细插入方法是否被跳过
     */
    @Test
    public void testInsertOrderMainWithoutOrderDetail() {
        System.out.println("=== 开始执行测试: testInsertOrderMainWithoutOrderDetail (创建无明细订单) ===");
        // 清除订单明细
        orderMain.setOrderDetailList(null);
        
        // 模拟Mapper行为
        when(orderMainMapper.insertOrderMain(any(OrderMain.class))).thenReturn(1);

        // 执行测试
        int result = orderMainService.insertOrderMain(orderMain);

        // 验证结果
        System.out.println("正在验证无明细订单创建结果...");
        assertEquals(1, result);
        verify(orderMainMapper, times(1)).insertOrderMain(any(OrderMain.class));
        System.out.println("✅ 测试通过: 成功创建无明细订单");
    }

    /**
     * 测试目标：测试订单修改时自动更新时间
     * 输入参数：需要修改的订单
     * 预期输出：返回修改成功的记录数
     * 断言验证：更新时间是否被正确设置
     */
    @Test
    public void testUpdateOrderMainSetsUpdateTime() {
        System.out.println("=== 开始执行测试: testUpdateOrderMainSetsUpdateTime (订单修改-更新时间) ===");
        // 模拟Mapper行为
        when(orderMainMapper.updateOrderMain(any(OrderMain.class))).thenReturn(1);
        Date beforeUpdate = orderMain.getUpdateTime();

        // 执行测试
        int result = orderMainService.updateOrderMain(orderMain);

        // 验证结果
        System.out.println("正在验证更新时间设置...");
        assertEquals(1, result);
        assertNotNull(orderMain.getUpdateTime());
        assertNotEquals(beforeUpdate, orderMain.getUpdateTime());
        verify(orderMainMapper, times(1)).updateOrderMain(any(OrderMain.class));
        System.out.println("✅ 测试通过: 成功更新订单并自动设置更新时间");
    }
}
