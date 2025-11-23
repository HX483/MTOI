package com.ruoyi.product.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品配方对象 product_formula
 * 
 * @author xihao
 * @date 2025-11-11
 */
public class ProductFormula extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配方ID */
    @Excel(name = "配方ID")
    private Long formulaId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 原料ID */
    @Excel(name = "原料ID")
    private Long materialId;

    /** 用量 */
    @Excel(name = "用量")
    private BigDecimal quantity;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    public void setFormulaId(Long formulaId) 
    {
        this.formulaId = formulaId;
    }

    public Long getFormulaId() 
    {
        return formulaId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
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
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("formulaId", getFormulaId())
            .append("productId", getProductId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("unit", getUnit())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
