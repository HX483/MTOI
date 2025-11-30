package com.ruoyi.purchase.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.purchase.domain.PurchaseDetail;
import com.ruoyi.purchase.service.IPurchaseDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 采购明细Controller
 * 
 * @author xihao
 * @date 2025-11-30
 */
@RestController
@RequestMapping("/purchase/detail")
public class PurchaseDetailController extends BaseController
{
    @Autowired
    private IPurchaseDetailService purchaseDetailService;

    /**
     * 查询采购明细列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseDetail purchaseDetail)
    {
        startPage();
        List<PurchaseDetail> list = purchaseDetailService.selectPurchaseDetailList(purchaseDetail);
        return getDataTable(list);
    }

    /**
     * 导出采购明细列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:detail:export')")
    @Log(title = "采购明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseDetail purchaseDetail)
    {
        List<PurchaseDetail> list = purchaseDetailService.selectPurchaseDetailList(purchaseDetail);
        ExcelUtil<PurchaseDetail> util = new ExcelUtil<PurchaseDetail>(PurchaseDetail.class);
        util.exportExcel(response, list, "采购明细数据");
    }

    /**
     * 获取采购明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchase:detail:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(purchaseDetailService.selectPurchaseDetailByDetailId(detailId));
    }

    /**
     * 新增采购明细
     */
    @PreAuthorize("@ss.hasPermi('purchase:detail:add')")
    @Log(title = "采购明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseDetail purchaseDetail)
    {
        return toAjax(purchaseDetailService.insertPurchaseDetail(purchaseDetail));
    }

    /**
     * 修改采购明细
     */
    @PreAuthorize("@ss.hasPermi('purchase:detail:edit')")
    @Log(title = "采购明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseDetail purchaseDetail)
    {
        return toAjax(purchaseDetailService.updatePurchaseDetail(purchaseDetail));
    }

    /**
     * 删除采购明细
     */
    @PreAuthorize("@ss.hasPermi('purchase:detail:remove')")
    @Log(title = "采购明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(purchaseDetailService.deletePurchaseDetailByDetailIds(detailIds));
    }
}
