package com.ruoyi.stock.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockRecordMapper;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.service.IStockRecordService;

/**
 * 库存变动记录Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-26
 */
@Service
public class StockRecordServiceImpl implements IStockRecordService 
{
    @Autowired
    private StockRecordMapper stockRecordMapper;

    /**
     * 查询库存变动记录
     * 
     * @param recordId 库存变动记录主键
     * @return 库存变动记录
     */
    @Override
    public StockRecord selectStockRecordByRecordId(Long recordId)
    {
        return stockRecordMapper.selectStockRecordByRecordId(recordId);
    }

    /**
     * 查询库存变动记录列表
     * 
     * @param stockRecord 库存变动记录
     * @return 库存变动记录
     */
    @Override
    public List<StockRecord> selectStockRecordList(StockRecord stockRecord)
    {
        return stockRecordMapper.selectStockRecordList(stockRecord);
    }

    /**
     * 新增库存变动记录
     * 
     * @param stockRecord 库存变动记录
     * @return 结果
     */
    @Override
    public int insertStockRecord(StockRecord stockRecord)
    {
        stockRecord.setCreateTime(DateUtils.getNowDate());
        return stockRecordMapper.insertStockRecord(stockRecord);
    }

    /**
     * 修改库存变动记录
     * 
     * @param stockRecord 库存变动记录
     * @return 结果
     */
    @Override
    public int updateStockRecord(StockRecord stockRecord)
    {
        return stockRecordMapper.updateStockRecord(stockRecord);
    }

    /**
     * 批量删除库存变动记录
     * 
     * @param recordIds 需要删除的库存变动记录主键
     * @return 结果
     */
    @Override
    public int deleteStockRecordByRecordIds(Long[] recordIds)
    {
        return stockRecordMapper.deleteStockRecordByRecordIds(recordIds);
    }

    /**
     * 删除库存变动记录信息
     * 
     * @param recordId 库存变动记录主键
     * @return 结果
     */
    @Override
    public int deleteStockRecordByRecordId(Long recordId)
    {
        return stockRecordMapper.deleteStockRecordByRecordId(recordId);
    }
}
