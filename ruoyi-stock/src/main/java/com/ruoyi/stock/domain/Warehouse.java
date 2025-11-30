package com.ruoyi.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库对象 warehouse
 * 
 * @author xihao
 * @date 2025-11-26
 */
public class Warehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String address;

    /** 负责人ID */
    @Excel(name = "负责人ID")
    private Long managerId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setWarehouseName(String warehouseName) 
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() 
    {
        return warehouseName;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setManagerId(Long managerId) 
    {
        this.managerId = managerId;
    }

    public Long getManagerId() 
    {
        return managerId;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("address", getAddress())
            .append("managerId", getManagerId())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
