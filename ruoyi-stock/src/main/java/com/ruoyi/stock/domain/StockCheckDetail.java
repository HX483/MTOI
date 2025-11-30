package com.ruoyi.stock.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存盘点明细对象 stock_check_detail
 * 
 * @author xihao
 * @date 2025-11-26
 */
public class StockCheckDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long detailId;

    /** 盘点ID */
    @Excel(name = "盘点ID")
    private Long checkId;

    /** 原料ID */
    @Excel(name = "原料ID")
    private Long materialId;

    /** 系统库存 */
    @Excel(name = "系统库存")
    private BigDecimal systemQuantity;

    /** 实际盘点数量 */
    @Excel(name = "实际盘点数量")
    private BigDecimal actualQuantity;

    /** 差异数量 */
    @Excel(name = "差异数量")
    private BigDecimal discrepancyQuantity;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 处理状态(1:未处理,2:已调整) */
    @Excel(name = "处理状态(1:未处理,2:已调整)")
    private Integer status;

    /** 调整时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调整时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date adjustmentTime;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setCheckId(Long checkId) 
    {
        this.checkId = checkId;
    }

    public Long getCheckId() 
    {
        return checkId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setSystemQuantity(BigDecimal systemQuantity) 
    {
        this.systemQuantity = systemQuantity;
    }

    public BigDecimal getSystemQuantity() 
    {
        return systemQuantity;
    }
    public void setActualQuantity(BigDecimal actualQuantity) 
    {
        this.actualQuantity = actualQuantity;
    }

    public BigDecimal getActualQuantity() 
    {
        return actualQuantity;
    }
    public void setDiscrepancyQuantity(BigDecimal discrepancyQuantity) 
    {
        this.discrepancyQuantity = discrepancyQuantity;
    }

    public BigDecimal getDiscrepancyQuantity() 
    {
        return discrepancyQuantity;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setAdjustmentTime(Date adjustmentTime) 
    {
        this.adjustmentTime = adjustmentTime;
    }

    public Date getAdjustmentTime() 
    {
        return adjustmentTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("checkId", getCheckId())
            .append("materialId", getMaterialId())
            .append("systemQuantity", getSystemQuantity())
            .append("actualQuantity", getActualQuantity())
            .append("discrepancyQuantity", getDiscrepancyQuantity())
            .append("unit", getUnit())
            .append("status", getStatus())
            .append("adjustmentTime", getAdjustmentTime())
            .append("remark", getRemark())
            .toString();
    }
}
