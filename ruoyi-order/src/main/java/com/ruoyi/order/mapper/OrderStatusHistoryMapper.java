package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.OrderStatusHistory;

/**
 * 订单状态历史Mapper接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface OrderStatusHistoryMapper 
{
    /**
     * 查询订单状态历史
     * 
     * @param historyId 订单状态历史主键
     * @return 订单状态历史
     */
    public OrderStatusHistory selectOrderStatusHistoryByHistoryId(Long historyId);

    /**
     * 查询订单状态历史列表
     * 
     * @param orderStatusHistory 订单状态历史
     * @return 订单状态历史集合
     */
    public List<OrderStatusHistory> selectOrderStatusHistoryList(OrderStatusHistory orderStatusHistory);

    /**
     * 新增订单状态历史
     * 
     * @param orderStatusHistory 订单状态历史
     * @return 结果
     */
    public int insertOrderStatusHistory(OrderStatusHistory orderStatusHistory);

    /**
     * 修改订单状态历史
     * 
     * @param orderStatusHistory 订单状态历史
     * @return 结果
     */
    public int updateOrderStatusHistory(OrderStatusHistory orderStatusHistory);

    /**
     * 删除订单状态历史
     * 
     * @param historyId 订单状态历史主键
     * @return 结果
     */
    public int deleteOrderStatusHistoryByHistoryId(Long historyId);

    /**
     * 批量删除订单状态历史
     * 
     * @param historyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderStatusHistoryByHistoryIds(Long[] historyIds);
}
