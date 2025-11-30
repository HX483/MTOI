package com.ruoyi.order.controller;

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
import com.ruoyi.order.domain.OrderStatusHistory;
import com.ruoyi.order.service.IOrderStatusHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单状态历史Controller
 * 
 * @author xihao
 * @date 2025-11-30
 */
@RestController
@RequestMapping("/order/history")
public class OrderStatusHistoryController extends BaseController
{
    @Autowired
    private IOrderStatusHistoryService orderStatusHistoryService;

    /**
     * 查询订单状态历史列表
     */
    @PreAuthorize("@ss.hasPermi('order:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderStatusHistory orderStatusHistory)
    {
        startPage();
        List<OrderStatusHistory> list = orderStatusHistoryService.selectOrderStatusHistoryList(orderStatusHistory);
        return getDataTable(list);
    }

    /**
     * 导出订单状态历史列表
     */
    @PreAuthorize("@ss.hasPermi('order:history:export')")
    @Log(title = "订单状态历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderStatusHistory orderStatusHistory)
    {
        List<OrderStatusHistory> list = orderStatusHistoryService.selectOrderStatusHistoryList(orderStatusHistory);
        ExcelUtil<OrderStatusHistory> util = new ExcelUtil<OrderStatusHistory>(OrderStatusHistory.class);
        util.exportExcel(response, list, "订单状态历史数据");
    }

    /**
     * 获取订单状态历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:history:query')")
    @GetMapping(value = "/{historyId}")
    public AjaxResult getInfo(@PathVariable("historyId") Long historyId)
    {
        return success(orderStatusHistoryService.selectOrderStatusHistoryByHistoryId(historyId));
    }

    /**
     * 新增订单状态历史
     */
    @PreAuthorize("@ss.hasPermi('order:history:add')")
    @Log(title = "订单状态历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderStatusHistory orderStatusHistory)
    {
        return toAjax(orderStatusHistoryService.insertOrderStatusHistory(orderStatusHistory));
    }

    /**
     * 修改订单状态历史
     */
    @PreAuthorize("@ss.hasPermi('order:history:edit')")
    @Log(title = "订单状态历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderStatusHistory orderStatusHistory)
    {
        return toAjax(orderStatusHistoryService.updateOrderStatusHistory(orderStatusHistory));
    }

    /**
     * 删除订单状态历史
     */
    @PreAuthorize("@ss.hasPermi('order:history:remove')")
    @Log(title = "订单状态历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{historyIds}")
    public AjaxResult remove(@PathVariable Long[] historyIds)
    {
        return toAjax(orderStatusHistoryService.deleteOrderStatusHistoryByHistoryIds(historyIds));
    }
}
