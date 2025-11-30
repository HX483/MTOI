package com.ruoyi.stock.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存信息对象 stock_info
 * 
 * @author xihao
 * @date 2025-11-26
 */
public class StockInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存ID */
    private Long stockId;

    /** 原料ID */
    @Excel(name = "原料ID")
    private Long materialId;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private BigDecimal quantity;

    /** 预警阈值 */
    @Excel(name = "预警阈值")
    private BigDecimal alertThreshold;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 存储位置 */
    @Excel(name = "存储位置")
    private String location;

    public void setStockId(Long stockId) 
    {
        this.stockId = stockId;
    }

    public Long getStockId() 
    {
        return stockId;
    }

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    public void setAlertThreshold(BigDecimal alertThreshold) 
    {
        this.alertThreshold = alertThreshold;
    }

    public BigDecimal getAlertThreshold() 
    {
        return alertThreshold;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("stockId", getStockId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("alertThreshold", getAlertThreshold())
            .append("warehouseId", getWarehouseId())
            .append("location", getLocation())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
