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
import com.ruoyi.stock.domain.StockCheck;
import com.ruoyi.stock.service.IStockCheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存盘点Controller
 * 
 * @author xihao
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/stock/check")
public class StockCheckController extends BaseController
{
    @Autowired
    private IStockCheckService stockCheckService;

    /**
     * 查询库存盘点列表
     */
    @PreAuthorize("@ss.hasPermi('stock:check:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockCheck stockCheck)
    {
        startPage();
        List<StockCheck> list = stockCheckService.selectStockCheckList(stockCheck);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点列表
     */
    @PreAuthorize("@ss.hasPermi('stock:check:export')")
    @Log(title = "库存盘点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockCheck stockCheck)
    {
        List<StockCheck> list = stockCheckService.selectStockCheckList(stockCheck);
        ExcelUtil<StockCheck> util = new ExcelUtil<StockCheck>(StockCheck.class);
        util.exportExcel(response, list, "库存盘点数据");
    }

    /**
     * 获取库存盘点详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:check:query')")
    @GetMapping(value = "/{checkId}")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId)
    {
        return success(stockCheckService.selectStockCheckByCheckId(checkId));
    }

    /**
     * 新增库存盘点
     */
    @PreAuthorize("@ss.hasPermi('stock:check:add')")
    @Log(title = "库存盘点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockCheck stockCheck)
    {
        return toAjax(stockCheckService.insertStockCheck(stockCheck));
    }

    /**
     * 修改库存盘点
     */
    @PreAuthorize("@ss.hasPermi('stock:check:edit')")
    @Log(title = "库存盘点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockCheck stockCheck)
    {
        return toAjax(stockCheckService.updateStockCheck(stockCheck));
    }

    /**
     * 预生成盘点单号
     */
    @PreAuthorize("@ss.hasPermi('stock:check:add')")
    @GetMapping("/generateCheckNo")
    public AjaxResult generateCheckNo()
    {
        return success(stockCheckService.generateUniqueCheckNo());
    }

    /**
     * 删除库存盘点
     */
    @PreAuthorize("@ss.hasPermi('stock:check:remove')")
    @Log(title = "库存盘点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{checkIds}")
    public AjaxResult remove(@PathVariable Long[] checkIds)
    {
        return toAjax(stockCheckService.deleteStockCheckByCheckIds(checkIds));
    }
}
