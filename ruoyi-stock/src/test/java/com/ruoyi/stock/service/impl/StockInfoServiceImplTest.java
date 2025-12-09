package com.ruoyi.stock.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.stock.domain.StockInfo;
import com.ruoyi.stock.mapper.StockInfoMapper;
import com.ruoyi.stock.service.IStockInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 库存信息服务实现类单元测试
 * 
 * @author xihao
 * @date 2025-11-26
 */
@ExtendWith(MockitoExtension.class)
public class StockInfoServiceImplTest {

    @Mock
    private StockInfoMapper stockInfoMapper;

    @InjectMocks
    private StockInfoServiceImpl stockInfoServiceImpl;

    private StockInfo mockStockInfo;
    private List<StockInfo> mockStockList;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
        mockStockInfo = new StockInfo();
        mockStockInfo.setStockId(1L);
        mockStockInfo.setMaterialId(1L);
        mockStockInfo.setQuantity(new BigDecimal(100));
        mockStockInfo.setAlertThreshold(new BigDecimal(50));
        mockStockInfo.setWarehouseId(1L);
        mockStockInfo.setLocation("A-1-01");
        mockStockInfo.setCreateTime(new Date());
        mockStockInfo.setUpdateTime(new Date());

        mockStockList = new ArrayList<>();
        mockStockList.add(mockStockInfo);
    }

    @Test
    public void testSelectStockInfoByStockId() {
        System.out.println("=== 开始执行测试: testSelectStockInfoByStockId (通过库存ID查询库存信息) ===");
        System.out.println("正在验证通过ID查询库存信息功能...");
        
        // 模拟Mapper调用
        when(stockInfoMapper.selectStockInfoByStockId(1L)).thenReturn(mockStockInfo);

        // 执行测试方法
        StockInfo result = stockInfoServiceImpl.selectStockInfoByStockId(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(1L, result.getStockId());
        assertEquals(1L, result.getMaterialId());
        assertEquals(new BigDecimal(100), result.getQuantity());
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).selectStockInfoByStockId(1L);
        
        System.out.println("✅ 测试通过: 通过ID查询库存信息功能正常");
    }

    @Test
    public void testSelectStockInfoByStockIdNotFound() {
        System.out.println("=== 开始执行测试: testSelectStockInfoByStockIdNotFound (通过不存在的库存ID查询) ===");
        System.out.println("正在验证通过不存在的ID查询库存信息功能...");
        
        // 模拟Mapper调用 - 返回null
        when(stockInfoMapper.selectStockInfoByStockId(999L)).thenReturn(null);

        // 执行测试方法
        StockInfo result = stockInfoServiceImpl.selectStockInfoByStockId(999L);

        // 验证结果
        assertNull(result);
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).selectStockInfoByStockId(999L);
        
        System.out.println("✅ 测试通过: 通过不存在的ID查询库存信息功能正常");
    }

    @Test
    public void testSelectStockInfoList() {
        System.out.println("=== 开始执行测试: testSelectStockInfoList (查询库存信息列表) ===");
        System.out.println("正在验证查询库存信息列表功能...");
        
        // 模拟Mapper调用
        when(stockInfoMapper.selectStockInfoList(any(StockInfo.class))).thenReturn(mockStockList);

        // 执行测试方法
        List<StockInfo> result = stockInfoServiceImpl.selectStockInfoList(new StockInfo());

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getStockId());
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).selectStockInfoList(any(StockInfo.class));
        
        System.out.println("✅ 测试通过: 查询库存信息列表功能正常");
    }

    @Test
    public void testSelectStockInfoListWithCondition() {
        System.out.println("=== 开始执行测试: testSelectStockInfoListWithCondition (带条件查询库存信息列表) ===");
        System.out.println("正在验证带条件查询库存信息列表功能...");
        
        // 创建查询条件
        StockInfo query = new StockInfo();
        query.setMaterialId(1L);

        // 模拟Mapper调用
        when(stockInfoMapper.selectStockInfoList(query)).thenReturn(mockStockList);

        // 执行测试方法
        List<StockInfo> result = stockInfoServiceImpl.selectStockInfoList(query);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getMaterialId());
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).selectStockInfoList(query);
        
        System.out.println("✅ 测试通过: 带条件查询库存信息列表功能正常");
    }

    @Test
    public void testInsertStockInfo() {
        System.out.println("=== 开始执行测试: testInsertStockInfo (新增库存信息) ===");
        System.out.println("正在验证新增库存信息功能...");
        
        // 模拟Mapper调用
        when(stockInfoMapper.insertStockInfo(any(StockInfo.class))).thenReturn(1);

        // 执行测试方法
        int result = stockInfoServiceImpl.insertStockInfo(mockStockInfo);

        // 验证结果
        assertEquals(1, result);
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).insertStockInfo(any(StockInfo.class));
        
        System.out.println("✅ 测试通过: 新增库存信息功能正常");
    }

    @Test
    public void testUpdateStockInfo() {
        System.out.println("=== 开始执行测试: testUpdateStockInfo (修改库存信息) ===");
        System.out.println("正在验证修改库存信息功能...");
        
        // 模拟Mapper调用
        when(stockInfoMapper.updateStockInfo(any(StockInfo.class))).thenReturn(1);

        // 修改库存数量
        mockStockInfo.setQuantity(new BigDecimal(200));

        // 执行测试方法
        int result = stockInfoServiceImpl.updateStockInfo(mockStockInfo);

        // 验证结果
        assertEquals(1, result);
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).updateStockInfo(any(StockInfo.class));
        
        System.out.println("✅ 测试通过: 修改库存信息功能正常");
    }

    @Test
    public void testDeleteStockInfoByStockId() {
        System.out.println("=== 开始执行测试: testDeleteStockInfoByStockId (删除库存信息) ===");
        System.out.println("正在验证删除库存信息功能...");
        
        // 模拟Mapper调用
        when(stockInfoMapper.deleteStockInfoByStockId(1L)).thenReturn(1);

        // 执行测试方法
        int result = stockInfoServiceImpl.deleteStockInfoByStockId(1L);

        // 验证结果
        assertEquals(1, result);
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).deleteStockInfoByStockId(1L);
        
        System.out.println("✅ 测试通过: 删除库存信息功能正常");
    }

    @Test
    public void testDeleteStockInfoByStockIds() {
        System.out.println("=== 开始执行测试: testDeleteStockInfoByStockIds (批量删除库存信息) ===");
        System.out.println("正在验证批量删除库存信息功能...");
        
        // 模拟Mapper调用
        when(stockInfoMapper.deleteStockInfoByStockIds(any(Long[].class))).thenReturn(2);

        // 执行测试方法
        Long[] stockIds = {1L, 2L};
        int result = stockInfoServiceImpl.deleteStockInfoByStockIds(stockIds);

        // 验证结果
        assertEquals(2, result);
        
        // 验证Mapper方法是否被调用
        verify(stockInfoMapper, times(1)).deleteStockInfoByStockIds(any(Long[].class));
        
        System.out.println("✅ 测试通过: 批量删除库存信息功能正常");
    }
}