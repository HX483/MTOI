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
import com.ruoyi.product.domain.ProductFormula;
import com.ruoyi.product.service.IProductFormulaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品配方Controller
 * 
 * @author xihao
 * @date 2025-11-11
 */
@RestController
@RequestMapping("/product/formula")
public class ProductFormulaController extends BaseController
{
    @Autowired
    private IProductFormulaService productFormulaService;

    /**
     * 查询产品配方列表
     */
    @PreAuthorize("@ss.hasPermi('product:formula:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductFormula productFormula)
    {
        startPage();
        List<ProductFormula> list = productFormulaService.selectProductFormulaList(productFormula);
        return getDataTable(list);
    }

    /**
     * 导出产品配方列表
     */
    @PreAuthorize("@ss.hasPermi('product:formula:export')")
    @Log(title = "产品配方", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductFormula productFormula)
    {
        List<ProductFormula> list = productFormulaService.selectProductFormulaList(productFormula);
        ExcelUtil<ProductFormula> util = new ExcelUtil<ProductFormula>(ProductFormula.class);
        util.exportExcel(response, list, "产品配方数据");
    }

    /**
     * 获取产品配方详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:formula:query')")
    @GetMapping(value = "/{formulaId}")
    public AjaxResult getInfo(@PathVariable("formulaId") Long formulaId)
    {
        return success(productFormulaService.selectProductFormulaByFormulaId(formulaId));
    }

    /**
     * 新增产品配方
     */
    @PreAuthorize("@ss.hasPermi('product:formula:add')")
    @Log(title = "产品配方", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductFormula productFormula)
    {
        return toAjax(productFormulaService.insertProductFormula(productFormula));
    }

    /**
     * 修改产品配方
     */
    @PreAuthorize("@ss.hasPermi('product:formula:edit')")
    @Log(title = "产品配方", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductFormula productFormula)
    {
        return toAjax(productFormulaService.updateProductFormula(productFormula));
    }

    /**
     * 删除产品配方
     */
    @PreAuthorize("@ss.hasPermi('product:formula:remove')")
    @Log(title = "产品配方", businessType = BusinessType.DELETE)
	@DeleteMapping("/{formulaIds}")
    public AjaxResult remove(@PathVariable Long[] formulaIds)
    {
        return toAjax(productFormulaService.deleteProductFormulaByFormulaIds(formulaIds));
    }
}
