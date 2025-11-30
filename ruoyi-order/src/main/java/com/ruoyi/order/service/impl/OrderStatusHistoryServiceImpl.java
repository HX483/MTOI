package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.OrderStatusHistoryMapper;
import com.ruoyi.order.domain.OrderStatusHistory;
import com.ruoyi.order.service.IOrderStatusHistoryService;

/**
 * 订单状态历史Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-30
 */
@Service
public class OrderStatusHistoryServiceImpl implements IOrderStatusHistoryService 
{
    @Autowired
    private OrderStatusHistoryMapper orderStatusHistoryMapper;

    /**
     * 查询订单状态历史
     * 
     * @param historyId 订单状态历史主键
     * @return 订单状态历史
     */
    @Override
    public OrderStatusHistory selectOrderStatusHistoryByHistoryId(Long historyId)
    {
        return orderStatusHistoryMapper.selectOrderStatusHistoryByHistoryId(historyId);
    }

    /**
     * 查询订单状态历史列表
     * 
     * @param orderStatusHistory 订单状态历史
     * @return 订单状态历史
     */
    @Override
    public List<OrderStatusHistory> selectOrderStatusHistoryList(OrderStatusHistory orderStatusHistory)
    {
        return orderStatusHistoryMapper.selectOrderStatusHistoryList(orderStatusHistory);
    }

    /**
     * 新增订单状态历史
     * 
     * @param orderStatusHistory 订单状态历史
     * @return 结果
     */
    @Override
    public int insertOrderStatusHistory(OrderStatusHistory orderStatusHistory)
    {
        orderStatusHistory.setCreateTime(DateUtils.getNowDate());
        return orderStatusHistoryMapper.insertOrderStatusHistory(orderStatusHistory);
    }

    /**
     * 修改订单状态历史
     * 
     * @param orderStatusHistory 订单状态历史
     * @return 结果
     */
    @Override
    public int updateOrderStatusHistory(OrderStatusHistory orderStatusHistory)
    {
        return orderStatusHistoryMapper.updateOrderStatusHistory(orderStatusHistory);
    }

    /**
     * 批量删除订单状态历史
     * 
     * @param historyIds 需要删除的订单状态历史主键
     * @return 结果
     */
    @Override
    public int deleteOrderStatusHistoryByHistoryIds(Long[] historyIds)
    {
        return orderStatusHistoryMapper.deleteOrderStatusHistoryByHistoryIds(historyIds);
    }

    /**
     * 删除订单状态历史信息
     * 
     * @param historyId 订单状态历史主键
     * @return 结果
     */
    @Override
    public int deleteOrderStatusHistoryByHistoryId(Long historyId)
    {
        return orderStatusHistoryMapper.deleteOrderStatusHistoryByHistoryId(historyId);
    }
}
