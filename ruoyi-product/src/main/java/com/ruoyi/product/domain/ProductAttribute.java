package com.ruoyi.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品属性对象 product_attribute
 * 
 * @author xihao
 * @date 2025-11-11
 */
public class ProductAttribute extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 属性ID */
    private Long attributeId;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String attributeName;

    /** 属性值(JSON格式) */
    @Excel(name = "属性值(JSON格式)")
    private String attributeValue;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    public void setAttributeId(Long attributeId) 
    {
        this.attributeId = attributeId;
    }

    public Long getAttributeId() 
    {
        return attributeId;
    }

    public void setAttributeName(String attributeName) 
    {
        this.attributeName = attributeName;
    }

    public String getAttributeName() 
    {
        return attributeName;
    }

    public void setAttributeValue(String attributeValue) 
    {
        this.attributeValue = attributeValue;
    }

    public String getAttributeValue() 
    {
        return attributeValue;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("attributeId", getAttributeId())
            .append("attributeName", getAttributeName())
            .append("attributeValue", getAttributeValue())
            .append("productId", getProductId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
