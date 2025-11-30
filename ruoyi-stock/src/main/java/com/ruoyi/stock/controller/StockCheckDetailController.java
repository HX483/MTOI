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
import com.ruoyi.stock.domain.StockCheckDetail;
import com.ruoyi.stock.service.IStockCheckDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存盘点明细Controller
 * 
 * @author xihao
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/stock/detail")
public class StockCheckDetailController extends BaseController
{
    @Autowired
    private IStockCheckDetailService stockCheckDetailService;

    /**
     * 查询库存盘点明细列表
     */
    @PreAuthorize("@ss.hasPermi('stock:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockCheckDetail stockCheckDetail)
    {
        startPage();
        List<StockCheckDetail> list = stockCheckDetailService.selectStockCheckDetailList(stockCheckDetail);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点明细列表
     */
    @PreAuthorize("@ss.hasPermi('stock:detail:export')")
    @Log(title = "库存盘点明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockCheckDetail stockCheckDetail)
    {
        List<StockCheckDetail> list = stockCheckDetailService.selectStockCheckDetailList(stockCheckDetail);
        ExcelUtil<StockCheckDetail> util = new ExcelUtil<StockCheckDetail>(StockCheckDetail.class);
        util.exportExcel(response, list, "库存盘点明细数据");
    }

    /**
     * 获取库存盘点明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:detail:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(stockCheckDetailService.selectStockCheckDetailByDetailId(detailId));
    }

    /**
     * 新增库存盘点明细
     */
    @PreAuthorize("@ss.hasPermi('stock:detail:add')")
    @Log(title = "库存盘点明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockCheckDetail stockCheckDetail)
    {
        return toAjax(stockCheckDetailService.insertStockCheckDetail(stockCheckDetail));
    }

    /**
     * 修改库存盘点明细
     */
    @PreAuthorize("@ss.hasPermi('stock:detail:edit')")
    @Log(title = "库存盘点明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockCheckDetail stockCheckDetail)
    {
        return toAjax(stockCheckDetailService.updateStockCheckDetail(stockCheckDetail));
    }

    /**
     * 删除库存盘点明细
     */
    @PreAuthorize("@ss.hasPermi('stock:detail:remove')")
    @Log(title = "库存盘点明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(stockCheckDetailService.deleteStockCheckDetailByDetailIds(detailIds));
    }
}
