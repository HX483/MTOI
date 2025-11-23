package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.ProductAttribute;

/**
 * 产品属性Mapper接口
 * 
 * @author xihao
 * @date 2025-11-11
 */
public interface ProductAttributeMapper 
{
    /**
     * 查询产品属性
     * 
     * @param attributeId 产品属性主键
     * @return 产品属性
     */
    public ProductAttribute selectProductAttributeByAttributeId(Long attributeId);

    /**
     * 查询产品属性列表
     * 
     * @param productAttribute 产品属性
     * @return 产品属性集合
     */
    public List<ProductAttribute> selectProductAttributeList(ProductAttribute productAttribute);

    /**
     * 新增产品属性
     * 
     * @param productAttribute 产品属性
     * @return 结果
     */
    public int insertProductAttribute(ProductAttribute productAttribute);

    /**
     * 修改产品属性
     * 
     * @param productAttribute 产品属性
     * @return 结果
     */
    public int updateProductAttribute(ProductAttribute productAttribute);

    /**
     * 删除产品属性
     * 
     * @param attributeId 产品属性主键
     * @return 结果
     */
    public int deleteProductAttributeByAttributeId(Long attributeId);

    /**
     * 批量删除产品属性
     * 
     * @param attributeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductAttributeByAttributeIds(Long[] attributeIds);
}
