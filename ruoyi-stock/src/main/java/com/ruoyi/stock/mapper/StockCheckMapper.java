package com.ruoyi.stock.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.stock.domain.StockCheck;
import com.ruoyi.stock.domain.StockCheckDetail;

/**
 * 库存盘点Mapper接口
 * 
 * @author xihao
 * @date 2025-11-26
 */
public interface StockCheckMapper 
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
     * 查询指定日期的最大盘点单号
     * 
     * @param dateStr 日期字符串，格式为yyyyMMdd
     * @return 最大的盘点单号
     */
    public String selectMaxCheckNoByDate(@Param("dateStr") String dateStr);

    /**
     * 删除库存盘点
     * 
     * @param checkId 库存盘点主键
     * @return 结果
     */
    public int deleteStockCheckByCheckId(Long checkId);

    /**
     * 批量删除库存盘点
     * 
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockCheckByCheckIds(Long[] checkIds);

    /**
     * 批量删除库存盘点明细
     * 
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockCheckDetailByDetailIds(Long[] checkIds);
    
    /**
     * 批量新增库存盘点明细
     * 
     * @param stockCheckDetailList 库存盘点明细列表
     * @return 结果
     */
    public int batchStockCheckDetail(List<StockCheckDetail> stockCheckDetailList);
    

    /**
     * 通过库存盘点主键删除库存盘点明细信息
     * 
     * @param checkId 库存盘点ID
     * @return 结果
     */
    public int deleteStockCheckDetailByDetailId(Long checkId);
}
