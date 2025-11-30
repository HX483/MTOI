package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.OrderMain;
import com.ruoyi.order.domain.OrderDetail;

/**
 * 客户订单Mapper接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface OrderMainMapper 
{
    /**
     * 查询客户订单
     * 
     * @param orderId 客户订单主键
     * @return 客户订单
     */
    public OrderMain selectOrderMainByOrderId(Long orderId);

    /**
     * 查询客户订单列表
     * 
     * @param orderMain 客户订单
     * @return 客户订单集合
     */
    public List<OrderMain> selectOrderMainList(OrderMain orderMain);

    /**
     * 新增客户订单
     * 
     * @param orderMain 客户订单
     * @return 结果
     */
    public int insertOrderMain(OrderMain orderMain);

    /**
     * 修改客户订单
     * 
     * @param orderMain 客户订单
     * @return 结果
     */
    public int updateOrderMain(OrderMain orderMain);

    /**
     * 删除客户订单
     * 
     * @param orderId 客户订单主键
     * @return 结果
     */
    public int deleteOrderMainByOrderId(Long orderId);

    /**
     * 批量删除客户订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderMainByOrderIds(Long[] orderIds);

    /**
     * 批量删除订单明细
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderDetailByOrderIds(Long[] orderIds);
    
    /**
     * 批量新增订单明细
     * 
     * @param orderDetailList 订单明细列表
     * @return 结果
     */
    public int batchOrderDetail(List<OrderDetail> orderDetailList);
    

    /**
     * 通过客户订单主键删除订单明细信息
     * 
     * @param orderId 客户订单ID
     * @return 结果
     */
    public int deleteOrderDetailByOrderId(Long orderId);
}
