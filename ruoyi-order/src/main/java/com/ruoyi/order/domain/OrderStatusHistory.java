package com.ruoyi.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单状态历史对象 order_status_history
 * 
 * @author xihao
 * @date 2025-11-30
 */
public class OrderStatusHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 历史ID */
    private Long historyId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 原状态 */
    @Excel(name = "原状态")
    private Integer oldStatus;

    /** 新状态 */
    @Excel(name = "新状态")
    private Integer newStatus;

    /** 操作人ID */
    @Excel(name = "操作人ID")
    private Long operatorId;

    public void setHistoryId(Long historyId) 
    {
        this.historyId = historyId;
    }

    public Long getHistoryId() 
    {
        return historyId;
    }

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setOldStatus(Integer oldStatus) 
    {
        this.oldStatus = oldStatus;
    }

    public Integer getOldStatus() 
    {
        return oldStatus;
    }

    public void setNewStatus(Integer newStatus) 
    {
        this.newStatus = newStatus;
    }

    public Integer getNewStatus() 
    {
        return newStatus;
    }

    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("historyId", getHistoryId())
            .append("orderId", getOrderId())
            .append("oldStatus", getOldStatus())
            .append("newStatus", getNewStatus())
            .append("operatorId", getOperatorId())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .toString();
    }
}
