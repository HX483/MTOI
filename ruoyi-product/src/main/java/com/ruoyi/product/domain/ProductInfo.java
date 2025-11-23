package com.ruoyi.product.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品信息对象 product_info
 * 
 * @author xihao
 * @date 2025-11-20
 */
public class ProductInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 产品描述 */
    @Excel(name = "产品描述")
    private String description;

    /** 产品图片 */
    @Excel(name = "产品图片")
    private String imageUrl;

    /** 是否热销 */
    @Excel(name = "是否热销")
    private Integer isHot;

    /** 是否新品 */
    @Excel(name = "是否新品")
    private Integer isNew;

    /** 状态(1:上架,0:下架) */
    @Excel(name = "状态(1:上架,0:下架)")
    private Integer status;

    /** 产品配方信息 */
    private List<ProductFormula> productFormulaList;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setIsHot(Integer isHot) 
    {
        this.isHot = isHot;
    }

    public Integer getIsHot() 
    {
        return isHot;
    }

    public void setIsNew(Integer isNew) 
    {
        this.isNew = isNew;
    }

    public Integer getIsNew() 
    {
        return isNew;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public List<ProductFormula> getProductFormulaList()
    {
        return productFormulaList;
    }

    public void setProductFormulaList(List<ProductFormula> productFormulaList)
    {
        this.productFormulaList = productFormulaList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("categoryId", getCategoryId())
            .append("price", getPrice())
            .append("description", getDescription())
            .append("imageUrl", getImageUrl())
            .append("isHot", getIsHot())
            .append("isNew", getIsNew())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("productFormulaList", getProductFormulaList())
            .toString();
    }
}
