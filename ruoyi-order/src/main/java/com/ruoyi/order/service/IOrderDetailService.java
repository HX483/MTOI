package com.ruoyi.order.service;

import java.util.List;
import com.ruoyi.order.domain.OrderDetail;

/**
 * 订单明细Service接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface IOrderDetailService 
{
    /**
     * 查询订单明细
     * 
     * @param detailId 订单明细主键
     * @return 订单明细
     */
    public OrderDetail selectOrderDetailByDetailId(Long detailId);

    /**
     * 查询订单明细列表
     * 
     * @param orderDetail 订单明细
     * @return 订单明细集合
     */
    public List<OrderDetail> selectOrderDetailList(OrderDetail orderDetail);

    /**
     * 新增订单明细
     * 
     * @param orderDetail 订单明细
     * @return 结果
     */
    public int insertOrderDetail(OrderDetail orderDetail);

    /**
     * 修改订单明细
     * 
     * @param orderDetail 订单明细
     * @return 结果
     */
    public int updateOrderDetail(OrderDetail orderDetail);

    /**
     * 批量删除订单明细
     * 
     * @param detailIds 需要删除的订单明细主键集合
     * @return 结果
     */
    public int deleteOrderDetailByDetailIds(Long[] detailIds);

    /**
     * 删除订单明细信息
     * 
     * @param detailId 订单明细主键
     * @return 结果
     */
    public int deleteOrderDetailByDetailId(Long detailId);
}
