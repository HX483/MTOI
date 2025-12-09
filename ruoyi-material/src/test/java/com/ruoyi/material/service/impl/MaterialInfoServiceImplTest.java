package com.ruoyi.material.service.impl;

import com.ruoyi.material.domain.MaterialInfo;
import com.ruoyi.material.domain.Supplier;
import com.ruoyi.material.mapper.MaterialInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;

/**
 * 原料信息服务层实现类单元测试
 *
 * @author xihao
 * @date 2025-12-09
 */
@ExtendWith(MockitoExtension.class)
public class MaterialInfoServiceImplTest {

    @Mock
    private MaterialInfoMapper materialInfoMapper;

    @InjectMocks
    private MaterialInfoServiceImpl materialInfoService;

    private MaterialInfo materialInfo;
    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        materialInfo = new MaterialInfo();
        materialInfo.setMaterialId(1L);
        materialInfo.setMaterialName("珍珠粉圆");
        materialInfo.setCategoryId(1L);
        materialInfo.setSpecification("500g/袋");
        materialInfo.setUnit("袋");
        materialInfo.setSupplierId(1L);
        materialInfo.setPurchasePrice(new BigDecimal(15.00));
        materialInfo.setShelfLife(180L);
        materialInfo.setWarningDays(30L);
        materialInfo.setStatus(0);

        supplier = new Supplier();
        supplier.setSupplierId(1L);
        supplier.setSupplierName("珍珠供应商");
        supplier.setContactPerson("张三");
        supplier.setContactPhone("13800138000");
        supplier.setEmail("zhangsan@example.com");
        supplier.setAddress("北京市朝阳区");
        supplier.setStatus(0);

        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier);
        materialInfo.setSupplierList(supplierList);
    }

    /**
     * 测试目标：测试根据ID查询原料信息
     * 输入参数：原料ID
     * 预期输出：返回对应的原料信息
     * 断言验证：原料ID和原料名称是否正确
     */
    @Test
    public void testSelectMaterialInfoByMaterialId() {
        System.out.println("=== 开始执行测试: testSelectMaterialInfoByMaterialId (根据ID查询原料信息) ===");
        // 模拟Mapper行为
        when(materialInfoMapper.selectMaterialInfoByMaterialId(1L)).thenReturn(materialInfo);

        // 执行测试
        MaterialInfo result = materialInfoService.selectMaterialInfoByMaterialId(1L);

        // 验证结果
        System.out.println("正在验证查询结果...");
        assertNotNull(result);
        assertEquals(1L, result.getMaterialId());
        assertEquals("珍珠粉圆", result.getMaterialName());
        verify(materialInfoMapper, times(1)).selectMaterialInfoByMaterialId(1L);
        System.out.println("✅ 测试通过: 成功根据ID查询到原料信息");
    }

    /**
     * 测试目标：测试查询原料信息列表
     * 输入参数：原料信息查询条件
     * 预期输出：返回符合条件的原料信息列表
     * 断言验证：列表是否包含预期的原料项
     */
    @Test
    public void testSelectMaterialInfoList() {
        System.out.println("=== 开始执行测试: testSelectMaterialInfoList (查询原料信息列表) ===");
        // 模拟数据
        List<MaterialInfo> materialInfoList = new ArrayList<>();
        materialInfoList.add(materialInfo);

        // 模拟Mapper行为
        when(materialInfoMapper.selectMaterialInfoList(any(MaterialInfo.class))).thenReturn(materialInfoList);

        // 执行测试
        List<MaterialInfo> result = materialInfoService.selectMaterialInfoList(new MaterialInfo());

        // 验证结果
        System.out.println("正在验证列表查询结果...");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("珍珠粉圆", result.get(0).getMaterialName());
        verify(materialInfoMapper, times(1)).selectMaterialInfoList(any(MaterialInfo.class));
        System.out.println("✅ 测试通过: 成功查询到原料信息列表");
    }

    /**
     * 测试目标：测试新增原料信息
     * 输入参数：原料信息对象
     * 预期输出：返回新增结果
     * 断言验证：是否成功新增，并检查创建时间和关联供应商是否自动设置
     */
    @Test
    public void testInsertMaterialInfo() {
        System.out.println("=== 开始执行测试: testInsertMaterialInfo (新增原料信息) ===");
        // 模拟Mapper行为
        when(materialInfoMapper.insertMaterialInfo(any(MaterialInfo.class))).thenReturn(1);
        when(materialInfoMapper.batchSupplier(anyList())).thenReturn(1);

        // 执行测试
        int result = materialInfoService.insertMaterialInfo(materialInfo);

        // 验证结果
        System.out.println("正在验证新增结果...");
        assertEquals(1, result);
        assertNotNull(materialInfo.getCreateTime());
        verify(materialInfoMapper, times(1)).insertMaterialInfo(any(MaterialInfo.class));
        verify(materialInfoMapper, times(1)).batchSupplier(anyList());
        System.out.println("✅ 测试通过: 成功新增原料信息及关联供应商");
    }

    /**
     * 测试目标：测试修改原料信息
     * 输入参数：更新后的原料信息对象
     * 预期输出：返回修改结果
     * 断言验证：是否成功修改，并检查更新时间和关联供应商是否自动更新
     */
    @Test
    public void testUpdateMaterialInfo() {
        System.out.println("=== 开始执行测试: testUpdateMaterialInfo (修改原料信息) ===");
        // 修改原料信息
        materialInfo.setMaterialName("黑糖珍珠");
        supplier.setSupplierName("黑糖供应商");

        // 模拟Mapper行为
        when(materialInfoMapper.updateMaterialInfo(any(MaterialInfo.class))).thenReturn(1);
        when(materialInfoMapper.batchSupplier(anyList())).thenReturn(1);

        // 执行测试
        int result = materialInfoService.updateMaterialInfo(materialInfo);

        // 验证结果
        System.out.println("正在验证修改结果...");
        assertEquals(1, result);
        assertEquals("黑糖珍珠", materialInfo.getMaterialName());
        assertNotNull(materialInfo.getUpdateTime());
        verify(materialInfoMapper, times(1)).deleteSupplierBySupplierId(1L);
        verify(materialInfoMapper, times(1)).batchSupplier(anyList());
        verify(materialInfoMapper, times(1)).updateMaterialInfo(any(MaterialInfo.class));
        System.out.println("✅ 测试通过: 成功修改原料信息及关联供应商");
    }

    /**
     * 测试目标：测试删除单个原料信息
     * 输入参数：原料ID
     * 预期输出：返回删除结果
     * 断言验证：是否成功删除，并检查关联供应商是否也被删除
     */
    @Test
    public void testDeleteMaterialInfoByMaterialId() {
        System.out.println("=== 开始执行测试: testDeleteMaterialInfoByMaterialId (删除单个原料信息) ===");
        // 模拟Mapper行为
        when(materialInfoMapper.deleteMaterialInfoByMaterialId(1L)).thenReturn(1);
        when(materialInfoMapper.deleteSupplierBySupplierId(1L)).thenReturn(1);

        // 执行测试
        int result = materialInfoService.deleteMaterialInfoByMaterialId(1L);

        // 验证结果
        System.out.println("正在验证删除结果...");
        assertEquals(1, result);
        verify(materialInfoMapper, times(1)).deleteSupplierBySupplierId(1L);
        verify(materialInfoMapper, times(1)).deleteMaterialInfoByMaterialId(1L);
        System.out.println("✅ 测试通过: 成功删除单个原料信息及关联供应商");
    }

    /**
     * 测试目标：测试批量删除原料信息
     * 输入参数：原料ID数组
     * 预期输出：返回删除结果
     * 断言验证：是否成功批量删除，并检查关联供应商是否也被批量删除
     */
    @Test
    public void testDeleteMaterialInfoByMaterialIds() {
        System.out.println("=== 开始执行测试: testDeleteMaterialInfoByMaterialIds (批量删除原料信息) ===");
        // 准备测试数据
        Long[] materialIds = {1L, 2L, 3L};

        // 模拟Mapper行为
        when(materialInfoMapper.deleteMaterialInfoByMaterialIds(materialIds)).thenReturn(3);
        when(materialInfoMapper.deleteSupplierBySupplierIds(materialIds)).thenReturn(3);

        // 执行测试
        int result = materialInfoService.deleteMaterialInfoByMaterialIds(materialIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(3, result);
        verify(materialInfoMapper, times(1)).deleteSupplierBySupplierIds(materialIds);
        verify(materialInfoMapper, times(1)).deleteMaterialInfoByMaterialIds(materialIds);
        System.out.println("✅ 测试通过: 成功批量删除原料信息及关联供应商");
    }

    /**
     * 测试目标：测试新增原料信息（无供应商）
     * 输入参数：无供应商的原料信息对象
     * 预期输出：返回新增结果
     * 断言验证：是否成功新增，且不调用供应商批量插入方法
     */
    @Test
    public void testInsertMaterialInfoWithoutSupplier() {
        System.out.println("=== 开始执行测试: testInsertMaterialInfoWithoutSupplier (新增无供应商的原料信息) ===");
        // 清除供应商列表
        materialInfo.setSupplierList(null);

        // 模拟Mapper行为
        when(materialInfoMapper.insertMaterialInfo(any(MaterialInfo.class))).thenReturn(1);

        // 执行测试
        int result = materialInfoService.insertMaterialInfo(materialInfo);

        // 验证结果
        System.out.println("正在验证无供应商新增结果...");
        assertEquals(1, result);
        assertNotNull(materialInfo.getCreateTime());
        verify(materialInfoMapper, times(1)).insertMaterialInfo(any(MaterialInfo.class));
        verify(materialInfoMapper, never()).batchSupplier(anyList());
        System.out.println("✅ 测试通过: 成功新增无供应商的原料信息");
    }
}
