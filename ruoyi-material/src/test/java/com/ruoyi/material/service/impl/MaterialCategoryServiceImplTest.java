package com.ruoyi.material.service.impl;

import com.ruoyi.material.domain.MaterialCategory;
import com.ruoyi.material.mapper.MaterialCategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * 原料分类服务层实现类单元测试
 *
 * @author xihao
 * @date 2025-12-09
 */
@ExtendWith(MockitoExtension.class)
public class MaterialCategoryServiceImplTest {

    @Mock
    private MaterialCategoryMapper materialCategoryMapper;

    @InjectMocks
    private MaterialCategoryServiceImpl materialCategoryService;

    private MaterialCategory materialCategory;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        materialCategory = new MaterialCategory();
        materialCategory.setCategoryId(1L);
        materialCategory.setCategoryName("珍珠类");
        materialCategory.setParentId(0L);
        materialCategory.setOrderNum(1);
        materialCategory.setStatus(0);
    }

    /**
     * 测试目标：测试根据ID查询原料分类
     * 输入参数：分类ID
     * 预期输出：返回对应的原料分类
     * 断言验证：分类ID和分类名称是否正确
     */
    @Test
    public void testSelectMaterialCategoryByCategoryId() {
        System.out.println("=== 开始执行测试: testSelectMaterialCategoryByCategoryId (根据ID查询原料分类) ===");
        // 模拟Mapper行为
        when(materialCategoryMapper.selectMaterialCategoryByCategoryId(1L)).thenReturn(materialCategory);

        // 执行测试
        MaterialCategory result = materialCategoryService.selectMaterialCategoryByCategoryId(1L);

        // 验证结果
        System.out.println("正在验证查询结果...");
        assertNotNull(result);
        assertEquals(1L, result.getCategoryId());
        assertEquals("珍珠类", result.getCategoryName());
        verify(materialCategoryMapper, times(1)).selectMaterialCategoryByCategoryId(1L);
        System.out.println("✅ 测试通过: 成功根据ID查询到原料分类");
    }

    /**
     * 测试目标：测试查询原料分类列表
     * 输入参数：原料分类查询条件
     * 预期输出：返回符合条件的原料分类列表
     * 断言验证：列表是否包含预期的分类项
     */
    @Test
    public void testSelectMaterialCategoryList() {
        System.out.println("=== 开始执行测试: testSelectMaterialCategoryList (查询原料分类列表) ===");
        // 模拟数据
        List<MaterialCategory> materialCategoryList = new ArrayList<>();
        materialCategoryList.add(materialCategory);

        // 模拟Mapper行为
        when(materialCategoryMapper.selectMaterialCategoryList(any(MaterialCategory.class))).thenReturn(materialCategoryList);

        // 执行测试
        List<MaterialCategory> result = materialCategoryService.selectMaterialCategoryList(new MaterialCategory());

        // 验证结果
        System.out.println("正在验证列表查询结果...");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("珍珠类", result.get(0).getCategoryName());
        verify(materialCategoryMapper, times(1)).selectMaterialCategoryList(any(MaterialCategory.class));
        System.out.println("✅ 测试通过: 成功查询到原料分类列表");
    }

    /**
     * 测试目标：测试新增原料分类
     * 输入参数：原料分类对象
     * 预期输出：返回新增结果
     * 断言验证：是否成功新增，并检查创建时间是否自动设置
     */
    @Test
    public void testInsertMaterialCategory() {
        System.out.println("=== 开始执行测试: testInsertMaterialCategory (新增原料分类) ===");
        // 模拟Mapper行为
        when(materialCategoryMapper.insertMaterialCategory(any(MaterialCategory.class))).thenReturn(1);

        // 执行测试
        int result = materialCategoryService.insertMaterialCategory(materialCategory);

        // 验证结果
        System.out.println("正在验证新增结果...");
        assertEquals(1, result);
        assertNotNull(materialCategory.getCreateTime());
        verify(materialCategoryMapper, times(1)).insertMaterialCategory(any(MaterialCategory.class));
        System.out.println("✅ 测试通过: 成功新增原料分类");
    }

    /**
     * 测试目标：测试修改原料分类
     * 输入参数：更新后的原料分类对象
     * 预期输出：返回修改结果
     * 断言验证：是否成功修改，并检查更新时间是否自动设置
     */
    @Test
    public void testUpdateMaterialCategory() {
        System.out.println("=== 开始执行测试: testUpdateMaterialCategory (修改原料分类) ===");
        // 修改原料分类信息
        materialCategory.setCategoryName("珍珠粉圆类");

        // 模拟Mapper行为
        when(materialCategoryMapper.updateMaterialCategory(any(MaterialCategory.class))).thenReturn(1);

        // 执行测试
        int result = materialCategoryService.updateMaterialCategory(materialCategory);

        // 验证结果
        System.out.println("正在验证修改结果...");
        assertEquals(1, result);
        assertEquals("珍珠粉圆类", materialCategory.getCategoryName());
        assertNotNull(materialCategory.getUpdateTime());
        verify(materialCategoryMapper, times(1)).updateMaterialCategory(any(MaterialCategory.class));
        System.out.println("✅ 测试通过: 成功修改原料分类");
    }

    /**
     * 测试目标：测试删除单个原料分类
     * 输入参数：分类ID
     * 预期输出：返回删除结果
     * 断言验证：是否成功删除
     */
    @Test
    public void testDeleteMaterialCategoryByCategoryId() {
        System.out.println("=== 开始执行测试: testDeleteMaterialCategoryByCategoryId (删除单个原料分类) ===");
        // 模拟Mapper行为
        when(materialCategoryMapper.deleteMaterialCategoryByCategoryId(1L)).thenReturn(1);

        // 执行测试
        int result = materialCategoryService.deleteMaterialCategoryByCategoryId(1L);

        // 验证结果
        System.out.println("正在验证删除结果...");
        assertEquals(1, result);
        verify(materialCategoryMapper, times(1)).deleteMaterialCategoryByCategoryId(1L);
        System.out.println("✅ 测试通过: 成功删除单个原料分类");
    }

    /**
     * 测试目标：测试批量删除原料分类
     * 输入参数：分类ID数组
     * 预期输出：返回删除结果
     * 断言验证：是否成功批量删除
     */
    @Test
    public void testDeleteMaterialCategoryByCategoryIds() {
        System.out.println("=== 开始执行测试: testDeleteMaterialCategoryByCategoryIds (批量删除原料分类) ===");
        // 准备测试数据
        Long[] categoryIds = {1L, 2L, 3L};

        // 模拟Mapper行为
        when(materialCategoryMapper.deleteMaterialCategoryByCategoryIds(categoryIds)).thenReturn(3);

        // 执行测试
        int result = materialCategoryService.deleteMaterialCategoryByCategoryIds(categoryIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(3, result);
        verify(materialCategoryMapper, times(1)).deleteMaterialCategoryByCategoryIds(categoryIds);
        System.out.println("✅ 测试通过: 成功批量删除原料分类");
    }
}
