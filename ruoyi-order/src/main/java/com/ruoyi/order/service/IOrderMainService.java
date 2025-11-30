package com.ruoyi.order.service;

import java.util.List;
import com.ruoyi.order.domain.OrderMain;

/**
 * 客户订单Service接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface IOrderMainService 
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
     * 批量删除客户订单
     * 
     * @param orderIds 需要删除的客户订单主键集合
     * @return 结果
     */
    public int deleteOrderMainByOrderIds(Long[] orderIds);

    /**
     * 删除客户订单信息
     * 
     * @param orderId 客户订单主键
     * @return 结果
     */
    public int deleteOrderMainByOrderId(Long orderId);
}
