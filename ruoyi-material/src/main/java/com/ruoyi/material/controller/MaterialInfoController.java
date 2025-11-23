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
import com.ruoyi.material.domain.MaterialInfo;
import com.ruoyi.material.service.IMaterialInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 原料信息Controller
 * 
 * @author xihao
 * @date 2025-11-14
 */
@RestController
@RequestMapping("/material/info")
public class MaterialInfoController extends BaseController
{
    @Autowired
    private IMaterialInfoService materialInfoService;

    /**
     * 查询原料信息列表
     */
    @PreAuthorize("@ss.hasPermi('material:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaterialInfo materialInfo)
    {
        startPage();
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        return getDataTable(list);
    }

    /**
     * 导出原料信息列表
     */
    @PreAuthorize("@ss.hasPermi('material:info:export')")
    @Log(title = "原料信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialInfo materialInfo)
    {
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        ExcelUtil<MaterialInfo> util = new ExcelUtil<MaterialInfo>(MaterialInfo.class);
        util.exportExcel(response, list, "原料信息数据");
    }

    /**
     * 获取原料信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('material:info:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return success(materialInfoService.selectMaterialInfoByMaterialId(materialId));
    }

    /**
     * 新增原料信息
     */
    @PreAuthorize("@ss.hasPermi('material:info:add')")
    @Log(title = "原料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialInfo materialInfo)
    {
        return toAjax(materialInfoService.insertMaterialInfo(materialInfo));
    }

    /**
     * 修改原料信息
     */
    @PreAuthorize("@ss.hasPermi('material:info:edit')")
    @Log(title = "原料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialInfo materialInfo)
    {
        return toAjax(materialInfoService.updateMaterialInfo(materialInfo));
    }

    /**
     * 删除原料信息
     */
    @PreAuthorize("@ss.hasPermi('material:info:remove')")
    @Log(title = "原料信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(materialInfoService.deleteMaterialInfoByMaterialIds(materialIds));
    }
}
