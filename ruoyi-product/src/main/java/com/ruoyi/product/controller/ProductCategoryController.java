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
import com.ruoyi.product.domain.ProductCategory;
import com.ruoyi.product.service.IProductCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 产品分类Controller
 * 
 * @author xihao
 * @date 2025-11-20
 */
@RestController
@RequestMapping("/product/category")
public class ProductCategoryController extends BaseController
{
    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @GetMapping("/list")
    public AjaxResult list(ProductCategory productCategory)
    {
        List<ProductCategory> list = productCategoryService.selectProductCategoryList(productCategory);
        return success(list);
    }

    /**
     * 导出产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('product:category:export')")
    @Log(title = "产品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductCategory productCategory)
    {
        List<ProductCategory> list = productCategoryService.selectProductCategoryList(productCategory);
        ExcelUtil<ProductCategory> util = new ExcelUtil<ProductCategory>(ProductCategory.class);
        util.exportExcel(response, list, "产品分类数据");
    }

    /**
     * 获取产品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(productCategoryService.selectProductCategoryByCategoryId(categoryId));
    }

    /**
     * 新增产品分类
     */
    @PreAuthorize("@ss.hasPermi('product:category:add')")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductCategory productCategory)
    {
        return toAjax(productCategoryService.insertProductCategory(productCategory));
    }

    /**
     * 修改产品分类
     */
    @PreAuthorize("@ss.hasPermi('product:category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductCategory productCategory)
    {
        return toAjax(productCategoryService.updateProductCategory(productCategory));
    }

    /**
     * 删除产品分类
     */
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(productCategoryService.deleteProductCategoryByCategoryIds(categoryIds));
    }
}
