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
import com.ruoyi.order.domain.OrderMain;
import com.ruoyi.order.service.IOrderMainService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户订单Controller
 * 
 * @author xihao
 * @date 2025-11-30
 */
@RestController
@RequestMapping("/order/main")
public class OrderMainController extends BaseController
{
    @Autowired
    private IOrderMainService orderMainService;

    /**
     * 查询客户订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:main:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderMain orderMain)
    {
        startPage();
        List<OrderMain> list = orderMainService.selectOrderMainList(orderMain);
        return getDataTable(list);
    }

    /**
     * 导出客户订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:main:export')")
    @Log(title = "客户订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderMain orderMain)
    {
        List<OrderMain> list = orderMainService.selectOrderMainList(orderMain);
        ExcelUtil<OrderMain> util = new ExcelUtil<OrderMain>(OrderMain.class);
        util.exportExcel(response, list, "客户订单数据");
    }

    /**
     * 获取客户订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:main:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(orderMainService.selectOrderMainByOrderId(orderId));
    }

    /**
     * 新增客户订单
     */
    @PreAuthorize("@ss.hasPermi('order:main:add')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderMain orderMain)
    {
        return toAjax(orderMainService.insertOrderMain(orderMain));
    }

    /**
     * 修改客户订单
     */
    @PreAuthorize("@ss.hasPermi('order:main:edit')")
    @Log(title = "客户订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderMain orderMain)
    {
        return toAjax(orderMainService.updateOrderMain(orderMain));
    }

    /**
     * 删除客户订单
     */
    @PreAuthorize("@ss.hasPermi('order:main:remove')")
    @Log(title = "客户订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderMainService.deleteOrderMainByOrderIds(orderIds));
    }
}
