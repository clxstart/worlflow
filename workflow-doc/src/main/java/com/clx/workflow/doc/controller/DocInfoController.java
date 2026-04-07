package com.clx.workflow.doc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.common.utils.SecurityUtils;
import com.clx.workflow.doc.domain.DocInfo;
import com.clx.workflow.doc.domain.DocVersion;
import com.clx.workflow.doc.service.IDocInfoService;
import com.clx.workflow.doc.service.IDocVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档信息Controller
 */
@RestController
@RequestMapping("/doc/info")
public class DocInfoController extends BaseController {

    @Autowired
    private IDocInfoService docInfoService;

    @Autowired
    private IDocVersionService docVersionService;

    @PreAuthorize("@ss.hasPermi('doc:info:list')")
    @GetMapping("/list")
    public AjaxResult list(DocInfo doc) {
        Page<DocInfo> page = docInfoService.selectDocPage(getPage(), doc);
        return AjaxResult.success(page);
    }

    @PreAuthorize("@ss.hasPermi('doc:info:query')")
    @GetMapping("/{docId}")
    public AjaxResult getInfo(@PathVariable Long docId) {
        DocInfo doc = docInfoService.getById(docId);
        if (doc != null) {
            docInfoService.incrementReadCount(docId);
        }
        return AjaxResult.success(doc);
    }

    @PreAuthorize("@ss.hasPermi('doc:info:list')")
    @GetMapping("/category/{categoryId}")
    public AjaxResult getByCategory(@PathVariable Long categoryId) {
        List<DocInfo> list = docInfoService.selectDocsByCategory(categoryId);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('doc:info:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DocInfo doc) {
        doc.setCreateUserId(SecurityUtils.getUserId());
        doc.setVersion(1L);
        doc.setReadCount(0);
        doc.setDownloadCount(0);
        return toAjax(docInfoService.save(doc));
    }

    @PreAuthorize("@ss.hasPermi('doc:info:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DocInfo doc) {
        DocInfo oldDoc = docInfoService.getById(doc.getDocId());
        if (oldDoc != null && doc.getFilePath() != null && !doc.getFilePath().equals(oldDoc.getFilePath())) {
            // Create new version
            DocVersion version = new DocVersion();
            version.setDocId(doc.getDocId());
            version.setFilePath(doc.getFilePath());
            version.setFileSize(doc.getFileSize());
            version.setCreateUserId(SecurityUtils.getUserId());
            docVersionService.createNewVersion(version);

            // Update version number
            doc.setVersion(oldDoc.getVersion() + 1);
        }
        return toAjax(docInfoService.updateById(doc));
    }

    @PreAuthorize("@ss.hasPermi('doc:info:download')")
    @PutMapping("/download/{docId}")
    public AjaxResult download(@PathVariable Long docId) {
        docInfoService.incrementDownloadCount(docId);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('doc:info:remove')")
    @DeleteMapping("/{docIds}")
    public AjaxResult remove(@PathVariable Long[] docIds) {
        return toAjax(docInfoService.removeBatchByIds(List.of(docIds)));
    }

    @PreAuthorize("@ss.hasPermi('doc:info:query')")
    @GetMapping("/version/{docId}")
    public AjaxResult getVersions(@PathVariable Long docId) {
        List<DocVersion> versions = docVersionService.selectVersionsByDocId(docId);
        return AjaxResult.success(versions);
    }
}