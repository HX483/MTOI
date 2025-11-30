package com.ruoyi.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.order.domain.OrderDetail;
import com.ruoyi.order.mapper.OrderMainMapper;
import com.ruoyi.order.domain.OrderMain;
import com.ruoyi.order.service.IOrderMainService;

/**
 * 客户订单Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-30
 */
@Service
public class OrderMainServiceImpl implements IOrderMainService 
{
    @Autowired
    private OrderMainMapper orderMainMapper;

    /**
     * 查询客户订单
     * 
     * @param orderId 客户订单主键
     * @return 客户订单
     */
    @Override
    public OrderMain selectOrderMainByOrderId(Long orderId)
    {
        return orderMainMapper.selectOrderMainByOrderId(orderId);
    }

    /**
     * 查询客户订单列表
     * 
     * @param orderMain 客户订单
     * @return 客户订单
     */
    @Override
    public List<OrderMain> selectOrderMainList(OrderMain orderMain)
    {
        return orderMainMapper.selectOrderMainList(orderMain);
    }

    /**
     * 新增客户订单
     * 
     * @param orderMain 客户订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrderMain(OrderMain orderMain)
    {
        orderMain.setCreateTime(DateUtils.getNowDate());
        int rows = orderMainMapper.insertOrderMain(orderMain);
        insertOrderDetail(orderMain);
        return rows;
    }

    /**
     * 修改客户订单
     * 
     * @param orderMain 客户订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrderMain(OrderMain orderMain)
    {
        orderMain.setUpdateTime(DateUtils.getNowDate());
        orderMainMapper.deleteOrderDetailByOrderId(orderMain.getOrderId());
        insertOrderDetail(orderMain);
        return orderMainMapper.updateOrderMain(orderMain);
    }

    /**
     * 批量删除客户订单
     * 
     * @param orderIds 需要删除的客户订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderMainByOrderIds(Long[] orderIds)
    {
        orderMainMapper.deleteOrderDetailByOrderIds(orderIds);
        return orderMainMapper.deleteOrderMainByOrderIds(orderIds);
    }

    /**
     * 删除客户订单信息
     * 
     * @param orderId 客户订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderMainByOrderId(Long orderId)
    {
        orderMainMapper.deleteOrderDetailByOrderId(orderId);
        return orderMainMapper.deleteOrderMainByOrderId(orderId);
    }

    /**
     * 新增订单明细信息
     * 
     * @param orderMain 客户订单对象
     */
    public void insertOrderDetail(OrderMain orderMain)
    {
        List<OrderDetail> orderDetailList = orderMain.getOrderDetailList();
        Long orderId = orderMain.getOrderId();
        if (StringUtils.isNotNull(orderDetailList))
        {
            List<OrderDetail> list = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetail : orderDetailList)
            {
                orderDetail.setOrderId(orderId);
                list.add(orderDetail);
            }
            if (list.size() > 0)
            {
                orderMainMapper.batchOrderDetail(list);
            }
        }
    }
}
