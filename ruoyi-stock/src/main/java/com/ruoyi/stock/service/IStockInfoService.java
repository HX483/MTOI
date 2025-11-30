package com.ruoyi.stock.service;

import java.util.List;
import com.ruoyi.stock.domain.StockInfo;

/**
 * 库存信息Service接口
 * 
 * @author xihao
 * @date 2025-11-26
 */
public interface IStockInfoService 
{
    /**
     * 查询库存信息
     * 
     * @param stockId 库存信息主键
     * @return 库存信息
     */
    public StockInfo selectStockInfoByStockId(Long stockId);

    /**
     * 查询库存信息列表
     * 
     * @param stockInfo 库存信息
     * @return 库存信息集合
     */
    public List<StockInfo> selectStockInfoList(StockInfo stockInfo);

    /**
     * 新增库存信息
     * 
     * @param stockInfo 库存信息
     * @return 结果
     */
    public int insertStockInfo(StockInfo stockInfo);

    /**
     * 修改库存信息
     * 
     * @param stockInfo 库存信息
     * @return 结果
     */
    public int updateStockInfo(StockInfo stockInfo);

    /**
     * 批量删除库存信息
     * 
     * @param stockIds 需要删除的库存信息主键集合
     * @return 结果
     */
    public int deleteStockInfoByStockIds(Long[] stockIds);

    /**
     * 删除库存信息信息
     * 
     * @param stockId 库存信息主键
     * @return 结果
     */
    public int deleteStockInfoByStockId(Long stockId);
}
