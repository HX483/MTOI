package com.ruoyi.stock.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.mapper.StockCheckDetailMapper;
import com.ruoyi.stock.domain.StockCheckDetail;
import com.ruoyi.stock.service.IStockCheckDetailService;

/**
 * 库存盘点明细Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-26
 */
@Service
public class StockCheckDetailServiceImpl implements IStockCheckDetailService 
{
    @Autowired
    private StockCheckDetailMapper stockCheckDetailMapper;

    /**
     * 查询库存盘点明细
     * 
     * @param detailId 库存盘点明细主键
     * @return 库存盘点明细
     */
    @Override
    public StockCheckDetail selectStockCheckDetailByDetailId(Long detailId)
    {
        return stockCheckDetailMapper.selectStockCheckDetailByDetailId(detailId);
    }

    /**
     * 查询库存盘点明细列表
     * 
     * @param stockCheckDetail 库存盘点明细
     * @return 库存盘点明细
     */
    @Override
    public List<StockCheckDetail> selectStockCheckDetailList(StockCheckDetail stockCheckDetail)
    {
        return stockCheckDetailMapper.selectStockCheckDetailList(stockCheckDetail);
    }

    /**
     * 新增库存盘点明细
     * 
     * @param stockCheckDetail 库存盘点明细
     * @return 结果
     */
    @Override
    public int insertStockCheckDetail(StockCheckDetail stockCheckDetail)
    {
        return stockCheckDetailMapper.insertStockCheckDetail(stockCheckDetail);
    }

    /**
     * 修改库存盘点明细
     * 
     * @param stockCheckDetail 库存盘点明细
     * @return 结果
     */
    @Override
    public int updateStockCheckDetail(StockCheckDetail stockCheckDetail)
    {
        return stockCheckDetailMapper.updateStockCheckDetail(stockCheckDetail);
    }

    /**
     * 批量删除库存盘点明细
     * 
     * @param detailIds 需要删除的库存盘点明细主键
     * @return 结果
     */
    @Override
    public int deleteStockCheckDetailByDetailIds(Long[] detailIds)
    {
        return stockCheckDetailMapper.deleteStockCheckDetailByDetailIds(detailIds);
    }

    /**
     * 删除库存盘点明细信息
     * 
     * @param detailId 库存盘点明细主键
     * @return 结果
     */
    @Override
    public int deleteStockCheckDetailByDetailId(Long detailId)
    {
        return stockCheckDetailMapper.deleteStockCheckDetailByDetailId(detailId);
    }
}
