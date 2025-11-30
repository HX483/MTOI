package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.OrderDetailMapper;
import com.ruoyi.order.domain.OrderDetail;
import com.ruoyi.order.service.IOrderDetailService;

/**
 * 订单明细Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-30
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService 
{
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 查询订单明细
     * 
     * @param detailId 订单明细主键
     * @return 订单明细
     */
    @Override
    public OrderDetail selectOrderDetailByDetailId(Long detailId)
    {
        return orderDetailMapper.selectOrderDetailByDetailId(detailId);
    }

    /**
     * 查询订单明细列表
     * 
     * @param orderDetail 订单明细
     * @return 订单明细
     */
    @Override
    public List<OrderDetail> selectOrderDetailList(OrderDetail orderDetail)
    {
        return orderDetailMapper.selectOrderDetailList(orderDetail);
    }

    /**
     * 新增订单明细
     * 
     * @param orderDetail 订单明细
     * @return 结果
     */
    @Override
    public int insertOrderDetail(OrderDetail orderDetail)
    {
        orderDetail.setCreateTime(DateUtils.getNowDate());
        return orderDetailMapper.insertOrderDetail(orderDetail);
    }

    /**
     * 修改订单明细
     * 
     * @param orderDetail 订单明细
     * @return 结果
     */
    @Override
    public int updateOrderDetail(OrderDetail orderDetail)
    {
        return orderDetailMapper.updateOrderDetail(orderDetail);
    }

    /**
     * 批量删除订单明细
     * 
     * @param detailIds 需要删除的订单明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailByDetailIds(Long[] detailIds)
    {
        return orderDetailMapper.deleteOrderDetailByDetailIds(detailIds);
    }

    /**
     * 删除订单明细信息
     * 
     * @param detailId 订单明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailByDetailId(Long detailId)
    {
        return orderDetailMapper.deleteOrderDetailByDetailId(detailId);
    }
}
