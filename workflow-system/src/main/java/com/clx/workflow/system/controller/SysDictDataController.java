package com.clx.workflow.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.system.domain.SysDictData;
import com.clx.workflow.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 字典数据 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {

    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 获取字典数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDictData dictData,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysDictData> page = dictDataService.lambdaQuery()
                .eq(dictData.getDictType() != null && !dictData.getDictType().isEmpty(), SysDictData::getDictType, dictData.getDictType())
                .like(dictData.getDictLabel() != null && !dictData.getDictLabel().isEmpty(), SysDictData::getDictLabel, dictData.getDictLabel())
                .eq(dictData.getStatus() != null && !dictData.getStatus().isEmpty(), SysDictData::getStatus, dictData.getStatus())
                .orderByAsc(SysDictData::getDictSort)
                .page(new Page<>(pageNum, pageSize));
        return success(page);
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping("/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return success(dictDataService.getById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据
     */
    @GetMapping("/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictDataService.selectDictDataByType(dictType);
        return success(data);
    }

    /**
     * 新增字典数据
     */
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dictData) {
        return toAjax(dictDataService.save(dictData));
    }

    /**
     * 修改字典数据
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dictData) {
        return toAjax(dictDataService.updateById(dictData));
    }

    /**
     * 删除字典数据
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        return toAjax(dictDataService.removeBatchByIds(Arrays.asList(dictCodes)));
    }
}