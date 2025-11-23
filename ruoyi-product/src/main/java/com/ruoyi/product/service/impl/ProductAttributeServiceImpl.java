package com.ruoyi.product.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProductAttributeMapper;
import com.ruoyi.product.domain.ProductAttribute;
import com.ruoyi.product.service.IProductAttributeService;

/**
 * 产品属性Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-11
 */
@Service
public class ProductAttributeServiceImpl implements IProductAttributeService 
{
    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    /**
     * 查询产品属性
     * 
     * @param attributeId 产品属性主键
     * @return 产品属性
     */
    @Override
    public ProductAttribute selectProductAttributeByAttributeId(Long attributeId)
    {
        return productAttributeMapper.selectProductAttributeByAttributeId(attributeId);
    }

    /**
     * 查询产品属性列表
     * 
     * @param productAttribute 产品属性
     * @return 产品属性
     */
    @Override
    public List<ProductAttribute> selectProductAttributeList(ProductAttribute productAttribute)
    {
        return productAttributeMapper.selectProductAttributeList(productAttribute);
    }

    /**
     * 新增产品属性
     * 
     * @param productAttribute 产品属性
     * @return 结果
     */
    @Override
    public int insertProductAttribute(ProductAttribute productAttribute)
    {
        productAttribute.setCreateTime(DateUtils.getNowDate());
        return productAttributeMapper.insertProductAttribute(productAttribute);
    }

    /**
     * 修改产品属性
     * 
     * @param productAttribute 产品属性
     * @return 结果
     */
    @Override
    public int updateProductAttribute(ProductAttribute productAttribute)
    {
        productAttribute.setUpdateTime(DateUtils.getNowDate());
        return productAttributeMapper.updateProductAttribute(productAttribute);
    }

    /**
     * 批量删除产品属性
     * 
     * @param attributeIds 需要删除的产品属性主键
     * @return 结果
     */
    @Override
    public int deleteProductAttributeByAttributeIds(Long[] attributeIds)
    {
        return productAttributeMapper.deleteProductAttributeByAttributeIds(attributeIds);
    }

    /**
     * 删除产品属性信息
     * 
     * @param attributeId 产品属性主键
     * @return 结果
     */
    @Override
    public int deleteProductAttributeByAttributeId(Long attributeId)
    {
        return productAttributeMapper.deleteProductAttributeByAttributeId(attributeId);
    }
}
