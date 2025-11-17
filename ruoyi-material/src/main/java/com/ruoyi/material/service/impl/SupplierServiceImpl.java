package com.ruoyi.material.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.material.mapper.SupplierMapper;
import com.ruoyi.material.domain.Supplier;
import com.ruoyi.material.service.ISupplierService;

/**
 * 供应商Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-14
 */
@Service
public class SupplierServiceImpl implements ISupplierService 
{
    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 查询供应商
     * 
     * @param supplierId 供应商主键
     * @return 供应商
     */
    @Override
    public Supplier selectSupplierBySupplierId(Long supplierId)
    {
        return supplierMapper.selectSupplierBySupplierId(supplierId);
    }

    /**
     * 查询供应商列表
     * 
     * @param supplier 供应商
     * @return 供应商
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier)
    {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增供应商
     * 
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier)
    {
        supplier.setCreateTime(DateUtils.getNowDate());
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 修改供应商
     * 
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier)
    {
        supplier.setUpdateTime(DateUtils.getNowDate());
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 批量删除供应商
     * 
     * @param supplierIds 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierBySupplierIds(Long[] supplierIds)
    {
        return supplierMapper.deleteSupplierBySupplierIds(supplierIds);
    }

    /**
     * 删除供应商信息
     * 
     * @param supplierId 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierBySupplierId(Long supplierId)
    {
        return supplierMapper.deleteSupplierBySupplierId(supplierId);
    }
}
