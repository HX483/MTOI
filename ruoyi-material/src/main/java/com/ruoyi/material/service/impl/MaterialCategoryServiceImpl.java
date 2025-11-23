package com.ruoyi.material.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.material.mapper.MaterialCategoryMapper;
import com.ruoyi.material.domain.MaterialCategory;
import com.ruoyi.material.service.IMaterialCategoryService;

/**
 * 原料分类Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-14
 */
@Service
public class MaterialCategoryServiceImpl implements IMaterialCategoryService 
{
    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;

    /**
     * 查询原料分类
     * 
     * @param categoryId 原料分类主键
     * @return 原料分类
     */
    @Override
    public MaterialCategory selectMaterialCategoryByCategoryId(Long categoryId)
    {
        return materialCategoryMapper.selectMaterialCategoryByCategoryId(categoryId);
    }

    /**
     * 查询原料分类列表
     * 
     * @param materialCategory 原料分类
     * @return 原料分类
     */
    @Override
    public List<MaterialCategory> selectMaterialCategoryList(MaterialCategory materialCategory)
    {
        return materialCategoryMapper.selectMaterialCategoryList(materialCategory);
    }

    /**
     * 新增原料分类
     * 
     * @param materialCategory 原料分类
     * @return 结果
     */
    @Override
    public int insertMaterialCategory(MaterialCategory materialCategory)
    {
        materialCategory.setCreateTime(DateUtils.getNowDate());
        return materialCategoryMapper.insertMaterialCategory(materialCategory);
    }

    /**
     * 修改原料分类
     * 
     * @param materialCategory 原料分类
     * @return 结果
     */
    @Override
    public int updateMaterialCategory(MaterialCategory materialCategory)
    {
        materialCategory.setUpdateTime(DateUtils.getNowDate());
        return materialCategoryMapper.updateMaterialCategory(materialCategory);
    }

    /**
     * 批量删除原料分类
     * 
     * @param categoryIds 需要删除的原料分类主键
     * @return 结果
     */
    @Override
    public int deleteMaterialCategoryByCategoryIds(Long[] categoryIds)
    {
        return materialCategoryMapper.deleteMaterialCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除原料分类信息
     * 
     * @param categoryId 原料分类主键
     * @return 结果
     */
    @Override
    public int deleteMaterialCategoryByCategoryId(Long categoryId)
    {
        return materialCategoryMapper.deleteMaterialCategoryByCategoryId(categoryId);
    }
}
