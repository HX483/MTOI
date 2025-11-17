package com.ruoyi.material.controller;

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
import com.ruoyi.material.domain.MaterialCategory;
import com.ruoyi.material.service.IMaterialCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 原料分类Controller
 * 
 * @author xihao
 * @date 2025-11-14
 */
@RestController
@RequestMapping("/material/category")
public class MaterialCategoryController extends BaseController
{
    @Autowired
    private IMaterialCategoryService materialCategoryService;

    /**
     * 查询原料分类列表
     */
    @PreAuthorize("@ss.hasPermi('material:category:list')")
    @GetMapping("/list")
    public AjaxResult list(MaterialCategory materialCategory)
    {
        List<MaterialCategory> list = materialCategoryService.selectMaterialCategoryList(materialCategory);
        return success(list);
    }

    /**
     * 导出原料分类列表
     */
    @PreAuthorize("@ss.hasPermi('material:category:export')")
    @Log(title = "原料分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialCategory materialCategory)
    {
        List<MaterialCategory> list = materialCategoryService.selectMaterialCategoryList(materialCategory);
        ExcelUtil<MaterialCategory> util = new ExcelUtil<MaterialCategory>(MaterialCategory.class);
        util.exportExcel(response, list, "原料分类数据");
    }

    /**
     * 获取原料分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('material:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(materialCategoryService.selectMaterialCategoryByCategoryId(categoryId));
    }

    /**
     * 新增原料分类
     */
    @PreAuthorize("@ss.hasPermi('material:category:add')")
    @Log(title = "原料分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialCategory materialCategory)
    {
        return toAjax(materialCategoryService.insertMaterialCategory(materialCategory));
    }

    /**
     * 修改原料分类
     */
    @PreAuthorize("@ss.hasPermi('material:category:edit')")
    @Log(title = "原料分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialCategory materialCategory)
    {
        return toAjax(materialCategoryService.updateMaterialCategory(materialCategory));
    }

    /**
     * 删除原料分类
     */
    @PreAuthorize("@ss.hasPermi('material:category:remove')")
    @Log(title = "原料分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(materialCategoryService.deleteMaterialCategoryByCategoryIds(categoryIds));
    }
}
