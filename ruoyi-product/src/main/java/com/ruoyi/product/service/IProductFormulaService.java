package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.ProductFormula;

/**
 * 产品配方Service接口
 * 
 * @author xihao
 * @date 2025-11-11
 */
public interface IProductFormulaService 
{
    /**
     * 查询产品配方
     * 
     * @param formulaId 产品配方主键
     * @return 产品配方
     */
    public ProductFormula selectProductFormulaByFormulaId(Long formulaId);

    /**
     * 查询产品配方列表
     * 
     * @param productFormula 产品配方
     * @return 产品配方集合
     */
    public List<ProductFormula> selectProductFormulaList(ProductFormula productFormula);

    /**
     * 新增产品配方
     * 
     * @param productFormula 产品配方
     * @return 结果
     */
    public int insertProductFormula(ProductFormula productFormula);

    /**
     * 修改产品配方
     * 
     * @param productFormula 产品配方
     * @return 结果
     */
    public int updateProductFormula(ProductFormula productFormula);

    /**
     * 批量删除产品配方
     * 
     * @param formulaIds 需要删除的产品配方主键集合
     * @return 结果
     */
    public int deleteProductFormulaByFormulaIds(Long[] formulaIds);

    /**
     * 删除产品配方信息
     * 
     * @param formulaId 产品配方主键
     * @return 结果
     */
    public int deleteProductFormulaByFormulaId(Long formulaId);
}
