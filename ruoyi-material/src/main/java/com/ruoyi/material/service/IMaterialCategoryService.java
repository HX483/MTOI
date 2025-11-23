package com.ruoyi.material.service;

import java.util.List;
import com.ruoyi.material.domain.MaterialCategory;

/**
 * 原料分类Service接口
 * 
 * @author xihao
 * @date 2025-11-14
 */
public interface IMaterialCategoryService 
{
    /**
     * 查询原料分类
     * 
     * @param categoryId 原料分类主键
     * @return 原料分类
     */
    public MaterialCategory selectMaterialCategoryByCategoryId(Long categoryId);

    /**
     * 查询原料分类列表
     * 
     * @param materialCategory 原料分类
     * @return 原料分类集合
     */
    public List<MaterialCategory> selectMaterialCategoryList(MaterialCategory materialCategory);

    /**
     * 新增原料分类
     * 
     * @param materialCategory 原料分类
     * @return 结果
     */
    public int insertMaterialCategory(MaterialCategory materialCategory);

    /**
     * 修改原料分类
     * 
     * @param materialCategory 原料分类
     * @return 结果
     */
    public int updateMaterialCategory(MaterialCategory materialCategory);

    /**
     * 批量删除原料分类
     * 
     * @param categoryIds 需要删除的原料分类主键集合
     * @return 结果
     */
    public int deleteMaterialCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除原料分类信息
     * 
     * @param categoryId 原料分类主键
     * @return 结果
     */
    public int deleteMaterialCategoryByCategoryId(Long categoryId);
}
