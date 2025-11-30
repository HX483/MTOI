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
import com.ruoyi.stock.domain.StockInfo;
import com.ruoyi.stock.service.IStockInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存信息Controller
 * 
 * @author xihao
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/stock/info")
public class StockInfoController extends BaseController
{
    @Autowired
    private IStockInfoService stockInfoService;

    /**
     * 查询库存信息列表
     */
    @PreAuthorize("@ss.hasPermi('stock:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockInfo stockInfo)
    {
        startPage();
        List<StockInfo> list = stockInfoService.selectStockInfoList(stockInfo);
        return getDataTable(list);
    }

    /**
     * 导出库存信息列表
     */
    @PreAuthorize("@ss.hasPermi('stock:info:export')")
    @Log(title = "库存信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockInfo stockInfo)
    {
        List<StockInfo> list = stockInfoService.selectStockInfoList(stockInfo);
        ExcelUtil<StockInfo> util = new ExcelUtil<StockInfo>(StockInfo.class);
        util.exportExcel(response, list, "库存信息数据");
    }

    /**
     * 获取库存信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:info:query')")
    @GetMapping(value = "/{stockId}")
    public AjaxResult getInfo(@PathVariable("stockId") Long stockId)
    {
        return success(stockInfoService.selectStockInfoByStockId(stockId));
    }

    /**
     * 新增库存信息
     */
    @PreAuthorize("@ss.hasPermi('stock:info:add')")
    @Log(title = "库存信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockInfo stockInfo)
    {
        return toAjax(stockInfoService.insertStockInfo(stockInfo));
    }

    /**
     * 修改库存信息
     */
    @PreAuthorize("@ss.hasPermi('stock:info:edit')")
    @Log(title = "库存信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockInfo stockInfo)
    {
        return toAjax(stockInfoService.updateStockInfo(stockInfo));
    }

    /**
     * 删除库存信息
     */
    @PreAuthorize("@ss.hasPermi('stock:info:remove')")
    @Log(title = "库存信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{stockIds}")
    public AjaxResult remove(@PathVariable Long[] stockIds)
    {
        return toAjax(stockInfoService.deleteStockInfoByStockIds(stockIds));
    }
}
