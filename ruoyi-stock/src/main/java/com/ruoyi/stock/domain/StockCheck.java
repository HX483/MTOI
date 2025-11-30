package com.ruoyi.stock.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存盘点对象 stock_check
 * 
 * @author xihao
 * @date 2025-11-26
 */
public class StockCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点ID */
    private Long checkId;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String checkNo;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 盘点日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkDate;

    /** 盘点类型(1:全部盘点,2:部分盘点) */
    @Excel(name = "盘点类型(1:全部盘点,2:部分盘点)")
    private Integer checkType;

    /** 盘点状态(1:进行中,2:已完成,3:已取消) */
    @Excel(name = "盘点状态(1:进行中,2:已完成,3:已取消)")
    private Integer status;

    /** 总盘点数量 */
    @Excel(name = "总盘点数量")
    private Long totalCount;

    /** 差异数量 */
    @Excel(name = "差异数量")
    private Long discrepancyCount;

    /** 操作人ID */
    private Long operatorId;

    /** 库存盘点明细信息 */
    private List<StockCheckDetail> stockCheckDetailList;

    public void setCheckId(Long checkId) 
    {
        this.checkId = checkId;
    }

    public Long getCheckId() 
    {
        return checkId;
    }

    public void setCheckNo(String checkNo) 
    {
        this.checkNo = checkNo;
    }

    public String getCheckNo() 
    {
        return checkNo;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setCheckDate(Date checkDate) 
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate() 
    {
        return checkDate;
    }

    public void setCheckType(Integer checkType) 
    {
        this.checkType = checkType;
    }

    public Integer getCheckType() 
    {
        return checkType;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setTotalCount(Long totalCount) 
    {
        this.totalCount = totalCount;
    }

    public Long getTotalCount() 
    {
        return totalCount;
    }

    public void setDiscrepancyCount(Long discrepancyCount) 
    {
        this.discrepancyCount = discrepancyCount;
    }

    public Long getDiscrepancyCount() 
    {
        return discrepancyCount;
    }

    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }

    public List<StockCheckDetail> getStockCheckDetailList()
    {
        return stockCheckDetailList;
    }

    public void setStockCheckDetailList(List<StockCheckDetail> stockCheckDetailList)
    {
        this.stockCheckDetailList = stockCheckDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkId", getCheckId())
            .append("checkNo", getCheckNo())
            .append("warehouseId", getWarehouseId())
            .append("checkDate", getCheckDate())
            .append("checkType", getCheckType())
            .append("status", getStatus())
            .append("totalCount", getTotalCount())
            .append("discrepancyCount", getDiscrepancyCount())
            .append("operatorId", getOperatorId())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("stockCheckDetailList", getStockCheckDetailList())
            .toString();
    }
}
