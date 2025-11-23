package com.ruoyi.product.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProductCategoryMapper;
import com.ruoyi.product.domain.ProductCategory;
import com.ruoyi.product.service.IProductCategoryService;

/**
 * 产品分类Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-20
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService 
{
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 查询产品分类
     * 
     * @param categoryId 产品分类主键
     * @return 产品分类
     */
    @Override
    public ProductCategory selectProductCategoryByCategoryId(Long categoryId)
    {
        return productCategoryMapper.selectProductCategoryByCategoryId(categoryId);
    }

    /**
     * 查询产品分类列表
     * 
     * @param productCategory 产品分类
     * @return 产品分类
     */
    @Override
    public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory)
    {
        return productCategoryMapper.selectProductCategoryList(productCategory);
    }

    /**
     * 新增产品分类
     * 
     * @param productCategory 产品分类
     * @return 结果
     */
    @Override
    public int insertProductCategory(ProductCategory productCategory)
    {
        productCategory.setCreateTime(DateUtils.getNowDate());
        return productCategoryMapper.insertProductCategory(productCategory);
    }

    /**
     * 修改产品分类
     * 
     * @param productCategory 产品分类
     * @return 结果
     */
    @Override
    public int updateProductCategory(ProductCategory productCategory)
    {
        productCategory.setUpdateTime(DateUtils.getNowDate());
        return productCategoryMapper.updateProductCategory(productCategory);
    }

    /**
     * 批量删除产品分类
     * 
     * @param categoryIds 需要删除的产品分类主键
     * @return 结果
     */
    @Override
    public int deleteProductCategoryByCategoryIds(Long[] categoryIds)
    {
        return productCategoryMapper.deleteProductCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除产品分类信息
     * 
     * @param categoryId 产品分类主键
     * @return 结果
     */
    @Override
    public int deleteProductCategoryByCategoryId(Long categoryId)
    {
        return productCategoryMapper.deleteProductCategoryByCategoryId(categoryId);
    }
}
