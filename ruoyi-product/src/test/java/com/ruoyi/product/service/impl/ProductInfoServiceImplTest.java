package com.ruoyi.product.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.product.domain.ProductFormula;
import com.ruoyi.product.domain.ProductInfo;
import com.ruoyi.product.mapper.ProductInfoMapper;
import com.ruoyi.product.service.IProductInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

/**
 * 产品信息Service业务层测试
 *
 * @author xihao
 * @date 2025-12-09
 */
@ExtendWith(MockitoExtension.class)
public class ProductInfoServiceImplTest {

    @InjectMocks
    private ProductInfoServiceImpl productInfoService;

    @Mock
    private ProductInfoMapper productInfoMapper;

    private ProductInfo productInfo;
    private List<ProductFormula> productFormulaList;

    /**
     * 测试数据初始化
     */
    @BeforeEach
    public void setUp() {
        // 初始化产品信息
        productInfo = new ProductInfo();
        productInfo.setProductId(1L);
        productInfo.setProductName("测试产品");
        productInfo.setCategoryId(1L);
        productInfo.setPrice(new BigDecimal(100.00));
        productInfo.setDescription("这是一个测试产品");
        productInfo.setImageUrl("http://example.com/image.jpg");
        productInfo.setIsHot(0);
        productInfo.setIsNew(1);
        productInfo.setStatus(1);

        // 初始化产品配方列表
        productFormulaList = new ArrayList<>();
        ProductFormula formula1 = new ProductFormula();
        formula1.setMaterialId(1L);
        formula1.setQuantity(new BigDecimal(10.00));
        formula1.setUnit("g");

        ProductFormula formula2 = new ProductFormula();
        formula2.setMaterialId(2L);
        formula2.setQuantity(new BigDecimal(5.00));
        formula2.setUnit("ml");

        productFormulaList.add(formula1);
        productFormulaList.add(formula2);
        productInfo.setProductFormulaList(productFormulaList);
    }

    /**
     * 测试目标：查询产品信息
     * 测试场景：根据产品ID查询产品信息
     * 输入参数：产品ID为1
     * 预期输出：返回对应的产品信息
     */
    @Test
    public void testSelectProductInfoByProductId() {
        System.out.println("测试：根据产品ID查询产品信息");
        
        // 模拟数据
        when(productInfoMapper.selectProductInfoByProductId(1L)).thenReturn(productInfo);

        // 执行测试
        ProductInfo result = productInfoService.selectProductInfoByProductId(1L);

        // 验证结果
        assertNotNull(result, "查询结果不应为空");
        assertEquals(1L, result.getProductId(), "产品ID应匹配");
        assertEquals("测试产品", result.getProductName(), "产品名称应匹配");
        verify(productInfoMapper, times(1)).selectProductInfoByProductId(1L);
        
        System.out.println("✅ 查询产品信息测试通过");
    }

    /**
     * 测试目标：查询产品信息列表
     * 测试场景：根据条件查询产品信息列表
     * 输入参数：产品名称为"测试产品"的查询条件
     * 预期输出：返回包含该产品的列表
     */
    @Test
    public void testSelectProductInfoList() {
        System.out.println("测试：根据条件查询产品信息列表");
        
        // 模拟数据
        List<ProductInfo> productList = new ArrayList<>();
        productList.add(productInfo);
        when(productInfoMapper.selectProductInfoList(any(ProductInfo.class))).thenReturn(productList);

        // 执行测试
        List<ProductInfo> result = productInfoService.selectProductInfoList(productInfo);

        // 验证结果
        assertNotNull(result, "查询结果列表不应为空");
        assertFalse(result.isEmpty(), "查询结果列表不应为空");
        assertEquals(1, result.size(), "查询结果列表大小应匹配");
        verify(productInfoMapper, times(1)).selectProductInfoList(any(ProductInfo.class));
        
        System.out.println("✅ 查询产品信息列表测试通过");
    }

    /**
     * 测试目标：新增产品信息
     * 测试场景：成功新增产品信息
     * 输入参数：有效的产品信息
     * 预期输出：返回新增成功的行数1
     */
    @Test
    public void testInsertProductInfo_Success() {
        System.out.println("测试：成功新增产品信息");
        
        // 模拟数据
        when(productInfoMapper.insertProductInfo(any(ProductInfo.class))).thenReturn(1);
        when(productInfoMapper.batchProductFormula(anyList())).thenReturn(2);

        // 执行测试
        int result = productInfoService.insertProductInfo(productInfo);

        // 验证结果
        assertEquals(1, result, "新增产品信息应返回成功行数1");
        verify(productInfoMapper, times(1)).insertProductInfo(any(ProductInfo.class));
        verify(productInfoMapper, times(1)).batchProductFormula(anyList());
        assertNotNull(productInfo.getCreateTime(), "创建时间应被设置");
        
        System.out.println("✅ 新增产品信息成功测试通过");
    }

    /**
     * 测试目标：新增产品信息
     * 测试场景：产品名称重复导致新增失败
     * 输入参数：产品名称重复的产品信息
     * 预期输出：抛出RuntimeException异常
     */
    @Test
    public void testInsertProductInfo_DuplicateName() {
        System.out.println("测试：产品名称重复导致新增失败");
        
        // 模拟数据
        when(productInfoMapper.insertProductInfo(any(ProductInfo.class))).thenThrow(DuplicateKeyException.class);

        // 执行测试并验证异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productInfoService.insertProductInfo(productInfo);
        });

        // 验证结果
        assertEquals("产品名称已存在，请使用其他名称", exception.getMessage(), "异常消息应匹配");
        verify(productInfoMapper, times(1)).insertProductInfo(any(ProductInfo.class));
        verify(productInfoMapper, never()).batchProductFormula(anyList());
        
        System.out.println("✅ 新增产品信息失败（名称重复）测试通过");
    }

    /**
     * 测试目标：修改产品信息
     * 测试场景：成功修改产品信息
     * 输入参数：有效的产品信息
     * 预期输出：返回修改成功的行数1
     */
    @Test
    public void testUpdateProductInfo_Success() {
        System.out.println("测试：成功修改产品信息");
        
        // 模拟数据
        when(productInfoMapper.updateProductInfo(any(ProductInfo.class))).thenReturn(1);
        when(productInfoMapper.deleteProductFormulaByProductId(anyLong())).thenReturn(2);
        when(productInfoMapper.batchProductFormula(anyList())).thenReturn(2);

        // 执行测试
        int result = productInfoService.updateProductInfo(productInfo);

        // 验证结果
        assertEquals(1, result, "修改产品信息应返回成功行数1");
        verify(productInfoMapper, times(1)).updateProductInfo(any(ProductInfo.class));
        verify(productInfoMapper, times(1)).deleteProductFormulaByProductId(anyLong());
        verify(productInfoMapper, times(1)).batchProductFormula(anyList());
        assertNotNull(productInfo.getUpdateTime(), "更新时间应被设置");
        
        System.out.println("✅ 修改产品信息成功测试通过");
    }

    /**
     * 测试目标：修改产品信息
     * 测试场景：产品名称重复导致修改失败
     * 输入参数：产品名称重复的产品信息
     * 预期输出：抛出RuntimeException异常
     */
    @Test
    public void testUpdateProductInfo_DuplicateName() {
        System.out.println("测试：产品名称重复导致修改失败");
        
        // 模拟数据
        when(productInfoMapper.deleteProductFormulaByProductId(anyLong())).thenReturn(2);
        when(productInfoMapper.batchProductFormula(anyList())).thenReturn(2);
        when(productInfoMapper.updateProductInfo(any(ProductInfo.class))).thenThrow(DuplicateKeyException.class);

        // 执行测试并验证异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productInfoService.updateProductInfo(productInfo);
        });

        // 验证结果
        assertEquals("产品名称已存在，请使用其他名称", exception.getMessage(), "异常消息应匹配");
        verify(productInfoMapper, times(1)).updateProductInfo(any(ProductInfo.class));
        
        System.out.println("✅ 修改产品信息失败（名称重复）测试通过");
    }

    /**
     * 测试目标：删除产品信息
     * 测试场景：成功删除单个产品信息
     * 输入参数：产品ID为1
     * 预期输出：返回删除成功的行数1
     */
    @Test
    public void testDeleteProductInfoByProductId() {
        System.out.println("测试：成功删除单个产品信息");
        
        // 模拟数据
        when(productInfoMapper.deleteProductFormulaByProductId(anyLong())).thenReturn(2);
        when(productInfoMapper.deleteProductInfoByProductId(anyLong())).thenReturn(1);

        // 执行测试
        int result = productInfoService.deleteProductInfoByProductId(1L);

        // 验证结果
        assertEquals(1, result, "删除产品信息应返回成功行数1");
        verify(productInfoMapper, times(1)).deleteProductFormulaByProductId(1L);
        verify(productInfoMapper, times(1)).deleteProductInfoByProductId(1L);
        
        System.out.println("✅ 删除单个产品信息测试通过");
    }

    /**
     * 测试目标：批量删除产品信息
     * 测试场景：成功批量删除产品信息
     * 输入参数：产品ID数组[1L, 2L]
     * 预期输出：返回删除成功的行数2
     */
    @Test
    public void testDeleteProductInfoByProductIds() {
        System.out.println("测试：成功批量删除产品信息");
        
        // 模拟数据
        Long[] productIds = {1L, 2L};
        when(productInfoMapper.deleteProductFormulaByProductIds(any(Long[].class))).thenReturn(4);
        when(productInfoMapper.deleteProductInfoByProductIds(any(Long[].class))).thenReturn(2);

        // 执行测试
        int result = productInfoService.deleteProductInfoByProductIds(productIds);

        // 验证结果
        assertEquals(2, result, "批量删除产品信息应返回成功行数2");
        verify(productInfoMapper, times(1)).deleteProductFormulaByProductIds(productIds);
        verify(productInfoMapper, times(1)).deleteProductInfoByProductIds(productIds);
        
        System.out.println("✅ 批量删除产品信息测试通过");
    }
}
