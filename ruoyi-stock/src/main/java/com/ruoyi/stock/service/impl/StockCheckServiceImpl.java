package com.ruoyi.stock.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.stock.domain.StockCheckDetail;
import com.ruoyi.stock.mapper.StockCheckMapper;
import com.ruoyi.stock.domain.StockCheck;
import com.ruoyi.stock.service.IStockCheckService;

/**
 * 库存盘点Service业务层处理
 * 
 * @author xihao
 * @date 2025-11-26
 */
@Service
public class StockCheckServiceImpl implements IStockCheckService 
{
    @Autowired
    private StockCheckMapper stockCheckMapper;

    /**
     * 查询库存盘点
     * 
     * @param checkId 库存盘点主键
     * @return 库存盘点
     */
    @Override
    public StockCheck selectStockCheckByCheckId(Long checkId)
    {
        return stockCheckMapper.selectStockCheckByCheckId(checkId);
    }

    /**
     * 查询库存盘点列表
     * 
     * @param stockCheck 库存盘点
     * @return 库存盘点
     */
    @Override
    public List<StockCheck> selectStockCheckList(StockCheck stockCheck)
    {
        return stockCheckMapper.selectStockCheckList(stockCheck);
    }

    /**
     * 新增库存盘点
     * 
     * @param stockCheck 库存盘点
     * @return 结果
     */
    @Transactional
    @Override
    public int insertStockCheck(StockCheck stockCheck)
    {
        // 如果没有提供checkNo，则自动生成一个
        if (stockCheck.getCheckNo() == null || stockCheck.getCheckNo().isEmpty()) {
            stockCheck.setCheckNo(generateUniqueCheckNo());
        }
        stockCheck.setCreateTime(DateUtils.getNowDate());
        int rows = stockCheckMapper.insertStockCheck(stockCheck);
        insertStockCheckDetail(stockCheck);
        return rows;
    }

    @Override
    public String generateUniqueCheckNo() {
        try {
            // 生成当前日期字符串（年月日格式）
            String dateStr = DateUtils.parseDateToStr("yyyyMMdd", DateUtils.getNowDate());
            // 查询数据库中今天已有的最大序号
            String maxCheckNo = stockCheckMapper.selectMaxCheckNoByDate(dateStr);
            int sequence = 1;

            // 如果今天已有记录，则提取序号并加1
            if (maxCheckNo != null && !maxCheckNo.isEmpty()) {
                // 提取序号部分（格式为：CK+日期+序号，例如：CK20250522001）
                String sequenceStr = maxCheckNo.substring(10); // 从第10位开始截取（索引从0开始）
                sequence = Integer.parseInt(sequenceStr) + 1;
            }

            // 格式化序号为3位数字，不足前面补0
            String formattedSequence = String.format("%03d", sequence);
            // 组合成最终的盘点单号
            return "CK" + dateStr + formattedSequence;
        } catch (Exception e) {
            // 异常时生成默认单号（如当前日期+001）
            String dateStr = DateUtils.parseDateToStr("yyyyMMdd", DateUtils.getNowDate());
            return "CK" + dateStr + "001";
        }
    }

    /**
     * 修改库存盘点
     * 
     * @param stockCheck 库存盘点
     * @return 结果
     */
    @Transactional
    @Override
    public int updateStockCheck(StockCheck stockCheck)
    {
        stockCheck.setUpdateTime(DateUtils.getNowDate());
        stockCheckMapper.deleteStockCheckDetailByDetailId(stockCheck.getCheckId());
        insertStockCheckDetail(stockCheck);
        return stockCheckMapper.updateStockCheck(stockCheck);
    }

    /**
     * 批量删除库存盘点
     * 
     * @param checkIds 需要删除的库存盘点主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStockCheckByCheckIds(Long[] checkIds)
    {
        stockCheckMapper.deleteStockCheckDetailByDetailIds(checkIds);
        return stockCheckMapper.deleteStockCheckByCheckIds(checkIds);
    }

    /**
     * 删除库存盘点信息
     * 
     * @param checkId 库存盘点主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteStockCheckByCheckId(Long checkId)
    {
        stockCheckMapper.deleteStockCheckDetailByDetailId(checkId);
        return stockCheckMapper.deleteStockCheckByCheckId(checkId);
    }

    /**
     * 新增库存盘点明细信息
     * 
     * @param stockCheck 库存盘点对象
     */
    public void insertStockCheckDetail(StockCheck stockCheck)
    {
        List<StockCheckDetail> stockCheckDetailList = stockCheck.getStockCheckDetailList();
        Long checkId = stockCheck.getCheckId();
        if (StringUtils.isNotNull(stockCheckDetailList))
        {
            List<StockCheckDetail> list = new ArrayList<StockCheckDetail>();
            for (StockCheckDetail stockCheckDetail : stockCheckDetailList)
            {
                stockCheckDetail.setCheckId(checkId);
                list.add(stockCheckDetail);
            }
            if (list.size() > 0)
            {
                stockCheckMapper.batchStockCheckDetail(list);
            }
        }
    }
}
