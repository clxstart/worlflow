package com.clx.workflow.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.doc.domain.DocInfo;
import com.clx.workflow.doc.mapper.DocInfoMapper;
import com.clx.workflow.doc.service.IDocInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文档信息Service实现
 */
@Service
public class DocInfoServiceImpl extends ServiceImpl<DocInfoMapper, DocInfo> implements IDocInfoService {

    @Override
    public Page<DocInfo> selectDocPage(Page<DocInfo> page, DocInfo doc) {
        LambdaQueryWrapper<DocInfo> wrapper = new LambdaQueryWrapper<>();
        if (doc.getDocName() != null && !doc.getDocName().isEmpty()) {
            wrapper.like(DocInfo::getDocName, doc.getDocName());
        }
        if (doc.getCategoryId() != null) {
            wrapper.eq(DocInfo::getCategoryId, doc.getCategoryId());
        }
        if (doc.getDocType() != null && !doc.getDocType().isEmpty()) {
            wrapper.eq(DocInfo::getDocType, doc.getDocType());
        }
        if (doc.getStatus() != null && !doc.getStatus().isEmpty()) {
            wrapper.eq(DocInfo::getStatus, doc.getStatus());
        }
        wrapper.orderByDesc(DocInfo::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public List<DocInfo> selectDocList(DocInfo doc) {
        LambdaQueryWrapper<DocInfo> wrapper = new LambdaQueryWrapper<>();
        if (doc.getDocName() != null && !doc.getDocName().isEmpty()) {
            wrapper.like(DocInfo::getDocName, doc.getDocName());
        }
        if (doc.getCategoryId() != null) {
            wrapper.eq(DocInfo::getCategoryId, doc.getCategoryId());
        }
        if (doc.getStatus() != null && !doc.getStatus().isEmpty()) {
            wrapper.eq(DocInfo::getStatus, doc.getStatus());
        }
        wrapper.orderByDesc(DocInfo::getCreateTime);
        return list(wrapper);
    }

    @Override
    public List<DocInfo> selectDocsByCategory(Long categoryId) {
        LambdaQueryWrapper<DocInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocInfo::getCategoryId, categoryId);
        wrapper.orderByDesc(DocInfo::getCreateTime);
        return list(wrapper);
    }

    @Override
    @Transactional
    public int incrementReadCount(Long docId) {
        LambdaUpdateWrapper<DocInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(DocInfo::getDocId, docId);
        wrapper.setSql("read_count = read_count + 1");
        return update(wrapper) ? 1 : 0;
    }

    @Override
    @Transactional
    public int incrementDownloadCount(Long docId) {
        LambdaUpdateWrapper<DocInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(DocInfo::getDocId, docId);
        wrapper.setSql("download_count = download_count + 1");
        return update(wrapper) ? 1 : 0;
    }
}