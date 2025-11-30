package com.ruoyi.purchase.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 采购订单对象 purchase_order
 * 
 * @author xihao
 * @date 2025-11-30
 */
public class PurchaseOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购订单ID */
    private Long purchaseId;

    /** 采购单号 */
    @Excel(name = "采购单号")
    private String purchaseNo;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 状态(1:待审核,2:已审核,3:采购中,4:已完成,5:已取消) */
    @Excel(name = "状态(1:待审核,2:已审核,3:采购中,4:已完成,5:已取消)")
    private Integer status;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long creatorId;

    /** 审核人ID */
    @Excel(name = "审核人ID")
    private Long auditorId;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionTime;

    /** 采购明细信息 */
    private List<PurchaseDetail> purchaseDetailList;

    public void setPurchaseId(Long purchaseId) 
    {
        this.purchaseId = purchaseId;
    }

    public Long getPurchaseId() 
    {
        return purchaseId;
    }

    public void setPurchaseNo(String purchaseNo) 
    {
        this.purchaseNo = purchaseNo;
    }

    public String getPurchaseNo() 
    {
        return purchaseNo;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setCreatorId(Long creatorId) 
    {
        this.creatorId = creatorId;
    }

    public Long getCreatorId() 
    {
        return creatorId;
    }

    public void setAuditorId(Long auditorId) 
    {
        this.auditorId = auditorId;
    }

    public Long getAuditorId() 
    {
        return auditorId;
    }

    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }

    public void setCompletionTime(Date completionTime) 
    {
        this.completionTime = completionTime;
    }

    public Date getCompletionTime() 
    {
        return completionTime;
    }

    public List<PurchaseDetail> getPurchaseDetailList()
    {
        return purchaseDetailList;
    }

    public void setPurchaseDetailList(List<PurchaseDetail> purchaseDetailList)
    {
        this.purchaseDetailList = purchaseDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("purchaseId", getPurchaseId())
            .append("purchaseNo", getPurchaseNo())
            .append("supplierId", getSupplierId())
            .append("totalAmount", getTotalAmount())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("creatorId", getCreatorId())
            .append("auditorId", getAuditorId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("auditTime", getAuditTime())
            .append("completionTime", getCompletionTime())
            .append("purchaseDetailList", getPurchaseDetailList())
            .toString();
    }
}
