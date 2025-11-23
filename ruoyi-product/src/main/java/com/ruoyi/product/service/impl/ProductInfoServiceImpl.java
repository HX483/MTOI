package com.ruoyi.product.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.product.domain.ProductFormula;
import com.ruoyi.product.mapper.ProductInfoMapper;
import com.ruoyi.product.domain.ProductInfo;
import com.ruoyi.product.service.IProductInfoService;

/**
 * 产品信息Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-20
 */
@Service
public class ProductInfoServiceImpl implements IProductInfoService 
{
    @Autowired
    private ProductInfoMapper productInfoMapper;

    /**
     * 查询产品信息
     * 
     * @param productId 产品信息主键
     * @return 产品信息
     */
    @Override
    public ProductInfo selectProductInfoByProductId(Long productId)
    {
        return productInfoMapper.selectProductInfoByProductId(productId);
    }

    /**
     * 查询产品信息列表
     * 
     * @param productInfo 产品信息
     * @return 产品信息
     */
    @Override
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo)
    {
        return productInfoMapper.selectProductInfoList(productInfo);
    }

    /**
     * 新增产品信息
     * 
     * @param productInfo 产品信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertProductInfo(ProductInfo productInfo)
    {
        productInfo.setCreateTime(DateUtils.getNowDate());
        int rows = productInfoMapper.insertProductInfo(productInfo);
        insertProductFormula(productInfo);
        return rows;
    }

    /**
     * 修改产品信息
     * 
     * @param productInfo 产品信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateProductInfo(ProductInfo productInfo)
    {
        productInfo.setUpdateTime(DateUtils.getNowDate());
        productInfoMapper.deleteProductFormulaByProductId(productInfo.getProductId());
        insertProductFormula(productInfo);
        return productInfoMapper.updateProductInfo(productInfo);
    }

    /**
     * 批量删除产品信息
     * 
     * @param productIds 需要删除的产品信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteProductInfoByProductIds(Long[] productIds)
    {
        productInfoMapper.deleteProductFormulaByProductIds(productIds);
        return productInfoMapper.deleteProductInfoByProductIds(productIds);
    }

    /**
     * 删除产品信息信息
     * 
     * @param productId 产品信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteProductInfoByProductId(Long productId)
    {
        productInfoMapper.deleteProductFormulaByProductId(productId);
        return productInfoMapper.deleteProductInfoByProductId(productId);
    }

    /**
     * 新增产品配方信息
     * 
     * @param productInfo 产品信息对象
     */
    public void insertProductFormula(ProductInfo productInfo)
    {
        List<ProductFormula> productFormulaList = productInfo.getProductFormulaList();
        Long productId = productInfo.getProductId();
        if (StringUtils.isNotNull(productFormulaList))
        {
            List<ProductFormula> list = new ArrayList<ProductFormula>();
            for (ProductFormula productFormula : productFormulaList)
            {
                productFormula.setProductId(productId);
                list.add(productFormula);
            }
            if (list.size() > 0)
            {
                productInfoMapper.batchProductFormula(list);
            }
        }
    }
}
