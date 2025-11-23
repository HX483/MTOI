package com.ruoyi.material.mapper;

import java.util.List;
import com.ruoyi.material.domain.MaterialInfo;
import com.ruoyi.material.domain.Supplier;

/**
 * 原料信息Mapper接口
 * 
 * @author xihao
 * @date 2025-11-14
 */
public interface MaterialInfoMapper 
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
     * 删除原料信息
     * 
     * @param materialId 原料信息主键
     * @return 结果
     */
    public int deleteMaterialInfoByMaterialId(Long materialId);

    /**
     * 批量删除原料信息
     * 
     * @param materialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialInfoByMaterialIds(Long[] materialIds);

    /**
     * 批量删除供应商
     * 
     * @param materialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierBySupplierIds(Long[] materialIds);
    
    /**
     * 批量新增供应商
     * 
     * @param supplierList 供应商列表
     * @return 结果
     */
    public int batchSupplier(List<Supplier> supplierList);
    

    /**
     * 通过原料信息主键删除供应商信息
     * 
     * @param materialId 原料信息ID
     * @return 结果
     */
    public int deleteSupplierBySupplierId(Long materialId);
}
