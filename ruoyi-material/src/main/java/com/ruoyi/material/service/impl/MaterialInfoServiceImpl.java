package com.ruoyi.material.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.material.domain.Supplier;
import com.ruoyi.material.mapper.MaterialInfoMapper;
import com.ruoyi.material.domain.MaterialInfo;
import com.ruoyi.material.service.IMaterialInfoService;

/**
 * 原料信息Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-14
 */
@Service
public class MaterialInfoServiceImpl implements IMaterialInfoService 
{
    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    /**
     * 查询原料信息
     * 
     * @param materialId 原料信息主键
     * @return 原料信息
     */
    @Override
    public MaterialInfo selectMaterialInfoByMaterialId(Long materialId)
    {
        return materialInfoMapper.selectMaterialInfoByMaterialId(materialId);
    }

    /**
     * 查询原料信息列表
     * 
     * @param materialInfo 原料信息
     * @return 原料信息
     */
    @Override
    public List<MaterialInfo> selectMaterialInfoList(MaterialInfo materialInfo)
    {
        return materialInfoMapper.selectMaterialInfoList(materialInfo);
    }

    /**
     * 新增原料信息
     * 
     * @param materialInfo 原料信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMaterialInfo(MaterialInfo materialInfo)
    {
        materialInfo.setCreateTime(DateUtils.getNowDate());
        int rows = materialInfoMapper.insertMaterialInfo(materialInfo);
        insertSupplier(materialInfo);
        return rows;
    }

    /**
     * 修改原料信息
     * 
     * @param materialInfo 原料信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMaterialInfo(MaterialInfo materialInfo)
    {
        materialInfo.setUpdateTime(DateUtils.getNowDate());
        materialInfoMapper.deleteSupplierBySupplierId(materialInfo.getMaterialId());
        insertSupplier(materialInfo);
        return materialInfoMapper.updateMaterialInfo(materialInfo);
    }

    /**
     * 批量删除原料信息
     * 
     * @param materialIds 需要删除的原料信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMaterialInfoByMaterialIds(Long[] materialIds)
    {
        materialInfoMapper.deleteSupplierBySupplierIds(materialIds);
        return materialInfoMapper.deleteMaterialInfoByMaterialIds(materialIds);
    }

    /**
     * 删除原料信息信息
     * 
     * @param materialId 原料信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMaterialInfoByMaterialId(Long materialId)
    {
        materialInfoMapper.deleteSupplierBySupplierId(materialId);
        return materialInfoMapper.deleteMaterialInfoByMaterialId(materialId);
    }

    /**
     * 新增供应商信息
     * 
     * @param materialInfo 原料信息对象
     */
    public void insertSupplier(MaterialInfo materialInfo)
    {
        List<Supplier> supplierList = materialInfo.getSupplierList();
        Long materialId = materialInfo.getMaterialId();
        if (StringUtils.isNotNull(supplierList))
        {
            List<Supplier> list = new ArrayList<Supplier>();
            for (Supplier supplier : supplierList)
            {
                // 修复：使用独立的主键，而不是复用materialId
                // supplier.setSupplierId(materialId);
                list.add(supplier);
            }
            if (list.size() > 0)
            {
                materialInfoMapper.batchSupplier(list);
            }
        }
    }
}