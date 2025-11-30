package com.ruoyi.stock.mapper;

import java.util.List;
import com.ruoyi.stock.domain.StockCheckDetail;

/**
 * 库存盘点明细Mapper接口
 * 
 * @author xihao
 * @date 2025-11-26
 */
public interface StockCheckDetailMapper 
{
    /**
     * 查询库存盘点明细
     * 
     * @param detailId 库存盘点明细主键
     * @return 库存盘点明细
     */
    public StockCheckDetail selectStockCheckDetailByDetailId(Long detailId);

    /**
     * 查询库存盘点明细列表
     * 
     * @param stockCheckDetail 库存盘点明细
     * @return 库存盘点明细集合
     */
    public List<StockCheckDetail> selectStockCheckDetailList(StockCheckDetail stockCheckDetail);

    /**
     * 新增库存盘点明细
     * 
     * @param stockCheckDetail 库存盘点明细
     * @return 结果
     */
    public int insertStockCheckDetail(StockCheckDetail stockCheckDetail);

    /**
     * 修改库存盘点明细
     * 
     * @param stockCheckDetail 库存盘点明细
     * @return 结果
     */
    public int updateStockCheckDetail(StockCheckDetail stockCheckDetail);

    /**
     * 删除库存盘点明细
     * 
     * @param detailId 库存盘点明细主键
     * @return 结果
     */
    public int deleteStockCheckDetailByDetailId(Long detailId);

    /**
     * 批量删除库存盘点明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockCheckDetailByDetailIds(Long[] detailIds);
}
