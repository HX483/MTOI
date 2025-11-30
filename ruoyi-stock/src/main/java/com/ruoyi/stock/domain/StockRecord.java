package com.ruoyi.stock.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存变动记录对象 stock_record
 * 
 * @author xihao
 * @date 2025-11-26
 */
public class StockRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 原料ID */
    @Excel(name = "原料ID")
    private Long materialId;

    /** 变动类型(1:入库,2:出库,3:盘点,4:报损) */
    @Excel(name = "变动类型(1:入库,2:出库,3:盘点,4:报损)")
    private Integer type;

    /** 变动数量 */
    @Excel(name = "变动数量")
    private BigDecimal quantity;

    /** 变动前数量 */
    @Excel(name = "变动前数量")
    private BigDecimal beforeQuantity;

    /** 变动后数量 */
    @Excel(name = "变动后数量")
    private BigDecimal afterQuantity;

    /** 关联订单ID */
    @Excel(name = "关联订单ID")
    private Long orderId;

    /** 参考单号 */
    @Excel(name = "参考单号")
    private String referenceNo;

    /** 操作人ID */
    @Excel(name = "操作人ID")
    private Long operatorId;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }

    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    public void setBeforeQuantity(BigDecimal beforeQuantity) 
    {
        this.beforeQuantity = beforeQuantity;
    }

    public BigDecimal getBeforeQuantity() 
    {
        return beforeQuantity;
    }

    public void setAfterQuantity(BigDecimal afterQuantity) 
    {
        this.afterQuantity = afterQuantity;
    }

    public BigDecimal getAfterQuantity() 
    {
        return afterQuantity;
    }

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setReferenceNo(String referenceNo) 
    {
        this.referenceNo = referenceNo;
    }

    public String getReferenceNo() 
    {
        return referenceNo;
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
            .append("recordId", getRecordId())
            .append("materialId", getMaterialId())
            .append("type", getType())
            .append("quantity", getQuantity())
            .append("beforeQuantity", getBeforeQuantity())
            .append("afterQuantity", getAfterQuantity())
            .append("orderId", getOrderId())
            .append("referenceNo", getReferenceNo())
            .append("operatorId", getOperatorId())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .toString();
    }
}
