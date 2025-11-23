package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.ProductCategory;

/**
 * 产品分类Service接口
 * 
 * @author xihao
 * @date 2025-11-20
 */
public interface IProductCategoryService 
{
    /**
     * 查询产品分类
     * 
     * @param categoryId 产品分类主键
     * @return 产品分类
     */
    public ProductCategory selectProductCategoryByCategoryId(Long categoryId);

    /**
     * 查询产品分类列表
     * 
     * @param productCategory 产品分类
     * @return 产品分类集合
     */
    public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory);

    /**
     * 新增产品分类
     * 
     * @param productCategory 产品分类
     * @return 结果
     */
    public int insertProductCategory(ProductCategory productCategory);

    /**
     * 修改产品分类
     * 
     * @param productCategory 产品分类
     * @return 结果
     */
    public int updateProductCategory(ProductCategory productCategory);

    /**
     * 批量删除产品分类
     * 
     * @param categoryIds 需要删除的产品分类主键集合
     * @return 结果
     */
    public int deleteProductCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除产品分类信息
     * 
     * @param categoryId 产品分类主键
     * @return 结果
     */
    public int deleteProductCategoryByCategoryId(Long categoryId);
}
