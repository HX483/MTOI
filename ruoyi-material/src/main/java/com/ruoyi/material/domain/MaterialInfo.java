package com.ruoyi.material.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料信息对象 material_info
 * 
 * @author xihao
 * @date 2025-11-14
 */
public class MaterialInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 原料ID */
    private Long materialId;

    /** 原料名称 */
    @Excel(name = "原料名称")
    private String materialName;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 采购单价 */
    @Excel(name = "采购单价")
    private BigDecimal purchasePrice;

    /** 保质期(天) */
    @Excel(name = "保质期(天)")
    private Long shelfLife;

    /** 预警天数 */
    @Excel(name = "预警天数")
    private Long warningDays;

    /** 状态(0:启用,1:禁用) */
    @Excel(name = "状态(0:启用,1:禁用)")
    private Integer status;

    /** 供应商信息 */
    private List<Supplier> supplierList;

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) 
    {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() 
    {
        return purchasePrice;
    }

    public void setShelfLife(Long shelfLife) 
    {
        this.shelfLife = shelfLife;
    }

    public Long getShelfLife() 
    {
        return shelfLife;
    }

    public void setWarningDays(Long warningDays) 
    {
        this.warningDays = warningDays;
    }

    public Long getWarningDays() 
    {
        return warningDays;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public List<Supplier> getSupplierList()
    {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList)
    {
        this.supplierList = supplierList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("categoryId", getCategoryId())
            .append("specification", getSpecification())
            .append("unit", getUnit())
            .append("supplierId", getSupplierId())
            .append("purchasePrice", getPurchasePrice())
            .append("shelfLife", getShelfLife())
            .append("warningDays", getWarningDays())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("supplierList", getSupplierList())
            .toString();
    }
}
