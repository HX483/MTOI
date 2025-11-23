package com.ruoyi.material.service;

import java.util.List;
import com.ruoyi.material.domain.MaterialInfo;

/**
 * 原料信息Service接口
 * 
 * @author xihao
 * @date 2025-11-14
 */
public interface IMaterialInfoService 
{
    /**
     * 查询原料信息
     * 
     * @param materialId 原料信息主键
     * @return 原料信息
     */
    public MaterialInfo selectMaterialInfoByMaterialId(Long materialId);

    /**
     * 查询原料信息列表
     * 
     * @param materialInfo 原料信息
     * @return 原料信息集合
     */
    public List<MaterialInfo> selectMaterialInfoList(MaterialInfo materialInfo);

    /**
     * 新增原料信息
     * 
     * @param materialInfo 原料信息
     * @return 结果
     */
    public int insertMaterialInfo(MaterialInfo materialInfo);

    /**
     * 修改原料信息
     * 
     * @param materialInfo 原料信息
     * @return 结果
     */
    public int updateMaterialInfo(MaterialInfo materialInfo);

    /**
     * 批量删除原料信息
     * 
     * @param materialIds 需要删除的原料信息主键集合
     * @return 结果
     */
    public int deleteMaterialInfoByMaterialIds(Long[] materialIds);

    /**
     * 删除原料信息信息
     * 
     * @param materialId 原料信息主键
     * @return 结果
     */
    public int deleteMaterialInfoByMaterialId(Long materialId);
}
