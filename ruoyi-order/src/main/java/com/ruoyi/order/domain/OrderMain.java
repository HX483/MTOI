package com.ruoyi.order.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户订单对象 order_main
 * 
 * @author xihao
 * @date 2025-11-30
 */
public class OrderMain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 顾客ID */
    @Excel(name = "顾客ID")
    private Long customerId;

    /** 顾客姓名 */
    @Excel(name = "顾客姓名")
    private String customerName;

    /** 顾客电话 */
    @Excel(name = "顾客电话")
    private String customerPhone;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 实际支付金额 */
    @Excel(name = "实际支付金额")
    private BigDecimal actualAmount;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private BigDecimal discountAmount;

    /** 订单类型(1:堂食,2:外卖,3:自提) */
    @Excel(name = "订单类型(1:堂食,2:外卖,3:自提)")
    private Integer orderType;

    /** 订单状态(1:待支付,2:待制作,3:制作中,4:待配送,5:配送中,6:已完成,7:已取消) */
    @Excel(name = "订单状态(1:待支付,2:待制作,3:制作中,4:待配送,5:配送中,6:已完成,7:已取消)")
    private Integer status;

    /** 支付状态(0:未支付,1:已支付,2:支付失败,3:退款中,4:已退款) */
    @Excel(name = "支付状态(0:未支付,1:已支付,2:支付失败,3:退款中,4:已退款)")
    private Integer paymentStatus;

    /** 支付方式(1:微信,2:支付宝,3:现金) */
    @Excel(name = "支付方式(1:微信,2:支付宝,3:现金)")
    private Integer paymentType;

    /** 桌号(堂食) */
    @Excel(name = "桌号(堂食)")
    private String tableNo;

    /** 配送地址(外卖) */
    @Excel(name = "配送地址(外卖)")
    private String deliveryAddress;

    /** 操作人ID */
    @Excel(name = "操作人ID")
    private Long operatorId;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionTime;

    /** 订单明细信息 */
    private List<OrderDetail> orderDetailList;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }

    public void setCustomerPhone(String customerPhone) 
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() 
    {
        return customerPhone;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) 
    {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getActualAmount() 
    {
        return actualAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) 
    {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountAmount() 
    {
        return discountAmount;
    }

    public void setOrderType(Integer orderType) 
    {
        this.orderType = orderType;
    }

    public Integer getOrderType() 
    {
        return orderType;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setPaymentStatus(Integer paymentStatus) 
    {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentStatus() 
    {
        return paymentStatus;
    }

    public void setPaymentType(Integer paymentType) 
    {
        this.paymentType = paymentType;
    }

    public Integer getPaymentType() 
    {
        return paymentType;
    }

    public void setTableNo(String tableNo) 
    {
        this.tableNo = tableNo;
    }

    public String getTableNo() 
    {
        return tableNo;
    }

    public void setDeliveryAddress(String deliveryAddress) 
    {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() 
    {
        return deliveryAddress;
    }

    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }

    public void setPaymentTime(Date paymentTime) 
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() 
    {
        return paymentTime;
    }

    public void setCompletionTime(Date completionTime) 
    {
        this.completionTime = completionTime;
    }

    public Date getCompletionTime() 
    {
        return completionTime;
    }

    public List<OrderDetail> getOrderDetailList()
    {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList)
    {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("customerPhone", getCustomerPhone())
            .append("totalAmount", getTotalAmount())
            .append("actualAmount", getActualAmount())
            .append("discountAmount", getDiscountAmount())
            .append("orderType", getOrderType())
            .append("status", getStatus())
            .append("paymentStatus", getPaymentStatus())
            .append("paymentType", getPaymentType())
            .append("tableNo", getTableNo())
            .append("deliveryAddress", getDeliveryAddress())
            .append("remark", getRemark())
            .append("operatorId", getOperatorId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("paymentTime", getPaymentTime())
            .append("completionTime", getCompletionTime())
            .append("orderDetailList", getOrderDetailList())
            .toString();
    }
}
