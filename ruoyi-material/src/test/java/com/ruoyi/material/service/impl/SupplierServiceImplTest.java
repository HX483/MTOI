package com.ruoyi.material.service.impl;

import com.ruoyi.material.domain.Supplier;
import com.ruoyi.material.mapper.SupplierMapper;
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
 * 供应商服务层实现类单元测试
 *
 * @author xihao
 * @date 2025-12-09
 */
@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        supplier = new Supplier();
        supplier.setSupplierId(1L);
        supplier.setSupplierName("优质原料供应商");
        supplier.setContactPerson("张经理");
        supplier.setContactPhone("13800138000");
        supplier.setAddress("上海市浦东新区");
        supplier.setStatus(0);
    }

    /**
     * 测试目标：测试根据ID查询供应商
     * 输入参数：供应商ID
     * 预期输出：返回对应的供应商
     * 断言验证：供应商ID和供应商名称是否正确
     */
    @Test
    public void testSelectSupplierBySupplierId() {
        System.out.println("=== 开始执行测试: testSelectSupplierBySupplierId (根据ID查询供应商) ===");
        // 模拟Mapper行为
        when(supplierMapper.selectSupplierBySupplierId(1L)).thenReturn(supplier);

        // 执行测试
        Supplier result = supplierService.selectSupplierBySupplierId(1L);

        // 验证结果
        System.out.println("正在验证查询结果...");
        assertNotNull(result);
        assertEquals(1L, result.getSupplierId());
        assertEquals("优质原料供应商", result.getSupplierName());
        verify(supplierMapper, times(1)).selectSupplierBySupplierId(1L);
        System.out.println("✅ 测试通过: 成功根据ID查询到供应商");
    }

    /**
     * 测试目标：测试查询供应商列表
     * 输入参数：供应商查询条件
     * 预期输出：返回符合条件的供应商列表
     * 断言验证：列表是否包含预期的供应商项
     */
    @Test
    public void testSelectSupplierList() {
        System.out.println("=== 开始执行测试: testSelectSupplierList (查询供应商列表) ===");
        // 模拟数据
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier);

        // 模拟Mapper行为
        when(supplierMapper.selectSupplierList(any(Supplier.class))).thenReturn(supplierList);

        // 执行测试
        List<Supplier> result = supplierService.selectSupplierList(new Supplier());

        // 验证结果
        System.out.println("正在验证列表查询结果...");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("优质原料供应商", result.get(0).getSupplierName());
        verify(supplierMapper, times(1)).selectSupplierList(any(Supplier.class));
        System.out.println("✅ 测试通过: 成功查询到供应商列表");
    }

    /**
     * 测试目标：测试新增供应商
     * 输入参数：供应商对象
     * 预期输出：返回新增结果
     * 断言验证：是否成功新增，并检查创建时间是否自动设置
     */
    @Test
    public void testInsertSupplier() {
        System.out.println("=== 开始执行测试: testInsertSupplier (新增供应商) ===");
        // 模拟Mapper行为
        when(supplierMapper.insertSupplier(any(Supplier.class))).thenReturn(1);

        // 执行测试
        int result = supplierService.insertSupplier(supplier);

        // 验证结果
        System.out.println("正在验证新增结果...");
        assertEquals(1, result);
        assertNotNull(supplier.getCreateTime());
        verify(supplierMapper, times(1)).insertSupplier(any(Supplier.class));
        System.out.println("✅ 测试通过: 成功新增供应商");
    }

    /**
     * 测试目标：测试修改供应商
     * 输入参数：更新后的供应商对象
     * 预期输出：返回修改结果
     * 断言验证：是否成功修改，并检查更新时间是否自动设置
     */
    @Test
    public void testUpdateSupplier() {
        System.out.println("=== 开始执行测试: testUpdateSupplier (修改供应商) ===");
        // 修改供应商信息
        supplier.setContactPerson("李经理");

        // 模拟Mapper行为
        when(supplierMapper.updateSupplier(any(Supplier.class))).thenReturn(1);

        // 执行测试
        int result = supplierService.updateSupplier(supplier);

        // 验证结果
        System.out.println("正在验证修改结果...");
        assertEquals(1, result);
        assertEquals("李经理", supplier.getContactPerson());
        assertNotNull(supplier.getUpdateTime());
        verify(supplierMapper, times(1)).updateSupplier(any(Supplier.class));
        System.out.println("✅ 测试通过: 成功修改供应商");
    }

    /**
     * 测试目标：测试删除单个供应商
     * 输入参数：供应商ID
     * 预期输出：返回删除结果
     * 断言验证：是否成功删除
     */
    @Test
    public void testDeleteSupplierBySupplierId() {
        System.out.println("=== 开始执行测试: testDeleteSupplierBySupplierId (删除单个供应商) ===");
        // 模拟Mapper行为
        when(supplierMapper.deleteSupplierBySupplierId(1L)).thenReturn(1);

        // 执行测试
        int result = supplierService.deleteSupplierBySupplierId(1L);

        // 验证结果
        System.out.println("正在验证删除结果...");
        assertEquals(1, result);
        verify(supplierMapper, times(1)).deleteSupplierBySupplierId(1L);
        System.out.println("✅ 测试通过: 成功删除单个供应商");
    }

    /**
     * 测试目标：测试批量删除供应商
     * 输入参数：供应商ID数组
     * 预期输出：返回删除结果
     * 断言验证：是否成功批量删除
     */
    @Test
    public void testDeleteSupplierBySupplierIds() {
        System.out.println("=== 开始执行测试: testDeleteSupplierBySupplierIds (批量删除供应商) ===");
        // 准备测试数据
        Long[] supplierIds = {1L, 2L, 3L};

        // 模拟Mapper行为
        when(supplierMapper.deleteSupplierBySupplierIds(supplierIds)).thenReturn(3);

        // 执行测试
        int result = supplierService.deleteSupplierBySupplierIds(supplierIds);

        // 验证结果
        System.out.println("正在验证批量删除结果...");
        assertEquals(3, result);
        verify(supplierMapper, times(1)).deleteSupplierBySupplierIds(supplierIds);
        System.out.println("✅ 测试通过: 成功批量删除供应商");
    }
}
