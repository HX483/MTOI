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
import com.ruoyi.product.domain.ProductAttribute;
import com.ruoyi.product.service.IProductAttributeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品属性Controller
 * 
 * @author xihao
 * @date 2025-11-11
 */
@RestController
@RequestMapping("/product/attribute")
public class ProductAttributeController extends BaseController
{
    @Autowired
    private IProductAttributeService productAttributeService;

    /**
     * 查询产品属性列表
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductAttribute productAttribute)
    {
        startPage();
        List<ProductAttribute> list = productAttributeService.selectProductAttributeList(productAttribute);
        return getDataTable(list);
    }

    /**
     * 导出产品属性列表
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:export')")
    @Log(title = "产品属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductAttribute productAttribute)
    {
        List<ProductAttribute> list = productAttributeService.selectProductAttributeList(productAttribute);
        ExcelUtil<ProductAttribute> util = new ExcelUtil<ProductAttribute>(ProductAttribute.class);
        util.exportExcel(response, list, "产品属性数据");
    }

    /**
     * 获取产品属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:query')")
    @GetMapping(value = "/{attributeId}")
    public AjaxResult getInfo(@PathVariable("attributeId") Long attributeId)
    {
        return success(productAttributeService.selectProductAttributeByAttributeId(attributeId));
    }

    /**
     * 新增产品属性
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:add')")
    @Log(title = "产品属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductAttribute productAttribute)
    {
        return toAjax(productAttributeService.insertProductAttribute(productAttribute));
    }

    /**
     * 修改产品属性
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:edit')")
    @Log(title = "产品属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductAttribute productAttribute)
    {
        return toAjax(productAttributeService.updateProductAttribute(productAttribute));
    }

    /**
     * 删除产品属性
     */
    @PreAuthorize("@ss.hasPermi('product:attribute:remove')")
    @Log(title = "产品属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{attributeIds}")
    public AjaxResult remove(@PathVariable Long[] attributeIds)
    {
        return toAjax(productAttributeService.deleteProductAttributeByAttributeIds(attributeIds));
    }
}
