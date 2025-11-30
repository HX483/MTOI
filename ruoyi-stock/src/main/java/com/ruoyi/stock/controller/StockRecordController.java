package com.ruoyi.stock.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.stock.domain.StockRecord;
import com.ruoyi.stock.service.IStockRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存变动记录Controller
 * 
 * @author xihao
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/stock/record")
public class StockRecordController extends BaseController
{
    @Autowired
    private IStockRecordService stockRecordService;

    /**
     * 查询库存变动记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockRecord stockRecord)
    {
        startPage();
        List<StockRecord> list = stockRecordService.selectStockRecordList(stockRecord);
        return getDataTable(list);
    }

    /**
     * 导出库存变动记录列表
     */
    @PreAuthorize("@ss.hasPermi('stock:record:export')")
    @Log(title = "库存变动记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockRecord stockRecord)
    {
        List<StockRecord> list = stockRecordService.selectStockRecordList(stockRecord);
        ExcelUtil<StockRecord> util = new ExcelUtil<StockRecord>(StockRecord.class);
        util.exportExcel(response, list, "库存变动记录数据");
    }

    /**
     * 获取库存变动记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(stockRecordService.selectStockRecordByRecordId(recordId));
    }

    /**
     * 新增库存变动记录
     */
    @PreAuthorize("@ss.hasPermi('stock:record:add')")
    @Log(title = "库存变动记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockRecord stockRecord)
    {
        return toAjax(stockRecordService.insertStockRecord(stockRecord));
    }

    /**
     * 修改库存变动记录
     */
    @PreAuthorize("@ss.hasPermi('stock:record:edit')")
    @Log(title = "库存变动记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockRecord stockRecord)
    {
        return toAjax(stockRecordService.updateStockRecord(stockRecord));
    }

    /**
     * 删除库存变动记录
     */
    @PreAuthorize("@ss.hasPermi('stock:record:remove')")
    @Log(title = "库存变动记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(stockRecordService.deleteStockRecordByRecordIds(recordIds));
    }
}
