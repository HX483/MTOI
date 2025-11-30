package com.ruoyi.stock.mapper;

import java.util.List;
import com.ruoyi.stock.domain.StockInfo;

/**
 * 库存信息Mapper接口
 * 
 * @author xihao
 * @date 2025-11-26
 */
public interface StockInfoMapper 
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
     * 删除库存信息
     * 
     * @param stockId 库存信息主键
     * @return 结果
     */
    public int deleteStockInfoByStockId(Long stockId);

    /**
     * 批量删除库存信息
     * 
     * @param stockIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockInfoByStockIds(Long[] stockIds);
}
