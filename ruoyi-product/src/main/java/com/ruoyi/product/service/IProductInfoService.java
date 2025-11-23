package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.ProductInfo;

/**
 * 产品信息Service接口
 * 
 * @author xihao
 * @date 2025-11-20
 */
public interface IProductInfoService 
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
     * 批量删除产品信息
     * 
     * @param productIds 需要删除的产品信息主键集合
     * @return 结果
     */
    public int deleteProductInfoByProductIds(Long[] productIds);

    /**
     * 删除产品信息信息
     * 
     * @param productId 产品信息主键
     * @return 结果
     */
    public int deleteProductInfoByProductId(Long productId);
}
