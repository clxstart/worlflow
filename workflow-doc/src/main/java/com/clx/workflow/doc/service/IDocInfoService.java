package com.clx.workflow.doc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.doc.domain.DocInfo;

import java.util.List;

/**
 * 文档信息Service接口
 */
public interface IDocInfoService extends IService<DocInfo> {

    Page<DocInfo> selectDocPage(Page<DocInfo> page, DocInfo doc);

    List<DocInfo> selectDocList(DocInfo doc);

    List<DocInfo> selectDocsByCategory(Long categoryId);

    int incrementReadCount(Long docId);

    int incrementDownloadCount(Long docId);
}