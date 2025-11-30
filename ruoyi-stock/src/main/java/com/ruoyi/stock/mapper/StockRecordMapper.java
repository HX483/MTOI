package com.ruoyi.stock.mapper;

import java.util.List;
import com.ruoyi.stock.domain.StockRecord;

/**
 * 库存变动记录Mapper接口
 * 
 * @author xihao
 * @date 2025-11-26
 */
public interface StockRecordMapper 
{
    /**
     * 查询库存变动记录
     * 
     * @param recordId 库存变动记录主键
     * @return 库存变动记录
     */
    public StockRecord selectStockRecordByRecordId(Long recordId);

    /**
     * 查询库存变动记录列表
     * 
     * @param stockRecord 库存变动记录
     * @return 库存变动记录集合
     */
    public List<StockRecord> selectStockRecordList(StockRecord stockRecord);

    /**
     * 新增库存变动记录
     * 
     * @param stockRecord 库存变动记录
     * @return 结果
     */
    public int insertStockRecord(StockRecord stockRecord);

    /**
     * 修改库存变动记录
     * 
     * @param stockRecord 库存变动记录
     * @return 结果
     */
    public int updateStockRecord(StockRecord stockRecord);

    /**
     * 删除库存变动记录
     * 
     * @param recordId 库存变动记录主键
     * @return 结果
     */
    public int deleteStockRecordByRecordId(Long recordId);

    /**
     * 批量删除库存变动记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockRecordByRecordIds(Long[] recordIds);
}
