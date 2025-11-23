package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.ProductInfo;
import com.ruoyi.product.domain.ProductFormula;

/**
 * 产品信息Mapper接口
 * 
 * @author xihao
 * @date 2025-11-20
 */
public interface ProductInfoMapper 
{
    /**
     * 查询产品信息
     * 
     * @param productId 产品信息主键
     * @return 产品信息
     */
    public ProductInfo selectProductInfoByProductId(Long productId);

    /**
     * 查询产品信息列表
     * 
     * @param productInfo 产品信息
     * @return 产品信息集合
     */
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo);

    /**
     * 新增产品信息
     * 
     * @param productInfo 产品信息
     * @return 结果
     */
    public int insertProductInfo(ProductInfo productInfo);

    /**
     * 修改产品信息
     * 
     * @param productInfo 产品信息
     * @return 结果
     */
    public int updateProductInfo(ProductInfo productInfo);

    /**
     * 删除产品信息
     * 
     * @param productId 产品信息主键
     * @return 结果
     */
    public int deleteProductInfoByProductId(Long productId);

    /**
     * 批量删除产品信息
     * 
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductInfoByProductIds(Long[] productIds);

    /**
     * 批量删除产品配方
     * 
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductFormulaByProductIds(Long[] productIds);
    
    /**
     * 批量新增产品配方
     * 
     * @param productFormulaList 产品配方列表
     * @return 结果
     */
    public int batchProductFormula(List<ProductFormula> productFormulaList);
    

    /**
     * 通过产品信息主键删除产品配方信息
     * 
     * @param productId 产品信息ID
     * @return 结果
     */
    public int deleteProductFormulaByProductId(Long productId);
}
