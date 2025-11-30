package com.ruoyi.stock.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockInfoMapper;
import com.ruoyi.stock.domain.StockInfo;
import com.ruoyi.stock.service.IStockInfoService;

/**
 * 库存信息Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-26
 */
@Service
public class StockInfoServiceImpl implements IStockInfoService 
{
    @Autowired
    private StockInfoMapper stockInfoMapper;

    /**
     * 查询库存信息
     * 
     * @param stockId 库存信息主键
     * @return 库存信息
     */
    @Override
    public StockInfo selectStockInfoByStockId(Long stockId)
    {
        return stockInfoMapper.selectStockInfoByStockId(stockId);
    }

    /**
     * 查询库存信息列表
     * 
     * @param stockInfo 库存信息
     * @return 库存信息
     */
    @Override
    public List<StockInfo> selectStockInfoList(StockInfo stockInfo)
    {
        return stockInfoMapper.selectStockInfoList(stockInfo);
    }

    /**
     * 新增库存信息
     * 
     * @param stockInfo 库存信息
     * @return 结果
     */
    @Override
    public int insertStockInfo(StockInfo stockInfo)
    {
        stockInfo.setCreateTime(DateUtils.getNowDate());
        return stockInfoMapper.insertStockInfo(stockInfo);
    }

    /**
     * 修改库存信息
     * 
     * @param stockInfo 库存信息
     * @return 结果
     */
    @Override
    public int updateStockInfo(StockInfo stockInfo)
    {
        stockInfo.setUpdateTime(DateUtils.getNowDate());
        return stockInfoMapper.updateStockInfo(stockInfo);
    }

    /**
     * 批量删除库存信息
     * 
     * @param stockIds 需要删除的库存信息主键
     * @return 结果
     */
    @Override
    public int deleteStockInfoByStockIds(Long[] stockIds)
    {
        return stockInfoMapper.deleteStockInfoByStockIds(stockIds);
    }

    /**
     * 删除库存信息信息
     * 
     * @param stockId 库存信息主键
     * @return 结果
     */
    @Override
    public int deleteStockInfoByStockId(Long stockId)
    {
        return stockInfoMapper.deleteStockInfoByStockId(stockId);
    }
}
