package com.ruoyi.stock.service;

import java.util.List;
import com.ruoyi.stock.domain.StockCheck;

/**
 * 库存盘点Service接口
 * 
 * @author xihao
 * @date 2025-11-26
 */
public interface IStockCheckService 
{
    /**
     * 查询库存盘点
     * 
     * @param checkId 库存盘点主键
     * @return 库存盘点
     */
    public StockCheck selectStockCheckByCheckId(Long checkId);

    /**
     * 查询库存盘点列表
     * 
     * @param stockCheck 库存盘点
     * @return 库存盘点集合
     */
    public List<StockCheck> selectStockCheckList(StockCheck stockCheck);

    /**
     * 新增库存盘点
     * 
     * @param stockCheck 库存盘点
     * @return 结果
     */
    public int insertStockCheck(StockCheck stockCheck);

    /**
     * 修改库存盘点
     * 
     * @param stockCheck 库存盘点
     * @return 结果
     */
    public int updateStockCheck(StockCheck stockCheck);

    /**
     * 生成唯一的盘点单号
     * 
     * @return 唯一的盘点单号
     */
    public String generateUniqueCheckNo();

    /**
     * 批量删除库存盘点
     * 
     * @param checkIds 需要删除的库存盘点主键集合
     * @return 结果
     */
    public int deleteStockCheckByCheckIds(Long[] checkIds);

    /**
     * 删除库存盘点信息
     * 
     * @param checkId 库存盘点主键
     * @return 结果
     */
    public int deleteStockCheckByCheckId(Long checkId);
}
