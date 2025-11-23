package com.ruoyi.product.controller;

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
import com.ruoyi.product.domain.ProductInfo;
import com.ruoyi.product.service.IProductInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品信息Controller
 * 
 * @author xihao
 * @date 2025-11-20
 */
@RestController
@RequestMapping("/product/info")
public class ProductInfoController extends BaseController
{
    @Autowired
    private IProductInfoService productInfoService;

    /**
     * 查询产品信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductInfo productInfo)
    {
        startPage();
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        return getDataTable(list);
    }

    /**
     * 导出产品信息列表
     */
    @PreAuthorize("@ss.hasPermi('product:info:export')")
    @Log(title = "产品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductInfo productInfo)
    {
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);
        util.exportExcel(response, list, "产品信息数据");
    }

    /**
     * 获取产品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(productInfoService.selectProductInfoByProductId(productId));
    }

    /**
     * 新增产品信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:add')")
    @Log(title = "产品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductInfo productInfo)
    {
        return toAjax(productInfoService.insertProductInfo(productInfo));
    }

    /**
     * 修改产品信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:edit')")
    @Log(title = "产品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductInfo productInfo)
    {
        return toAjax(productInfoService.updateProductInfo(productInfo));
    }

    /**
     * 删除产品信息
     */
    @PreAuthorize("@ss.hasPermi('product:info:remove')")
    @Log(title = "产品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(productInfoService.deleteProductInfoByProductIds(productIds));
    }
}
