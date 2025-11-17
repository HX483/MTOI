package com.ruoyi.product.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProductFormulaMapper;
import com.ruoyi.product.domain.ProductFormula;
import com.ruoyi.product.service.IProductFormulaService;

/**
 * 产品配方Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-11
 */
@Service
public class ProductFormulaServiceImpl implements IProductFormulaService 
{
    @Autowired
    private ProductFormulaMapper productFormulaMapper;

    /**
     * 查询产品配方
     * 
     * @param formulaId 产品配方主键
     * @return 产品配方
     */
    @Override
    public ProductFormula selectProductFormulaByFormulaId(Long formulaId)
    {
        return productFormulaMapper.selectProductFormulaByFormulaId(formulaId);
    }

    /**
     * 查询产品配方列表
     * 
     * @param productFormula 产品配方
     * @return 产品配方
     */
    @Override
    public List<ProductFormula> selectProductFormulaList(ProductFormula productFormula)
    {
        return productFormulaMapper.selectProductFormulaList(productFormula);
    }

    /**
     * 新增产品配方
     * 
     * @param productFormula 产品配方
     * @return 结果
     */
    @Override
    public int insertProductFormula(ProductFormula productFormula)
    {
        productFormula.setCreateTime(DateUtils.getNowDate());
        return productFormulaMapper.insertProductFormula(productFormula);
    }

    /**
     * 修改产品配方
     * 
     * @param productFormula 产品配方
     * @return 结果
     */
    @Override
    public int updateProductFormula(ProductFormula productFormula)
    {
        productFormula.setUpdateTime(DateUtils.getNowDate());
        return productFormulaMapper.updateProductFormula(productFormula);
    }

    /**
     * 批量删除产品配方
     * 
     * @param formulaIds 需要删除的产品配方主键
     * @return 结果
     */
    @Override
    public int deleteProductFormulaByFormulaIds(Long[] formulaIds)
    {
        return productFormulaMapper.deleteProductFormulaByFormulaIds(formulaIds);
    }

    /**
     * 删除产品配方信息
     * 
     * @param formulaId 产品配方主键
     * @return 结果
     */
    @Override
    public int deleteProductFormulaByFormulaId(Long formulaId)
    {
        return productFormulaMapper.deleteProductFormulaByFormulaId(formulaId);
    }
}
