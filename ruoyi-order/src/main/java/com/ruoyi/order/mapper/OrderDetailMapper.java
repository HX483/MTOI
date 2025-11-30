package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.OrderDetail;

/**
 * 订单明细Mapper接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface OrderDetailMapper 
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
     * 删除订单明细
     * 
     * @param detailId 订单明细主键
     * @return 结果
     */
    public int deleteOrderDetailByDetailId(Long detailId);

    /**
     * 批量删除订单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderDetailByDetailIds(Long[] detailIds);
}
