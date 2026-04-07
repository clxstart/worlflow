package com.clx.workflow.doc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.doc.domain.DocVersion;
import com.clx.workflow.doc.mapper.DocVersionMapper;
import com.clx.workflow.doc.service.IDocVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档版本Service实现
 */
@Service
public class DocVersionServiceImpl extends ServiceImpl<DocVersionMapper, DocVersion> implements IDocVersionService {

    @Override
    public List<DocVersion> selectVersionsByDocId(Long docId) {
        LambdaQueryWrapper<DocVersion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocVersion::getDocId, docId);
        wrapper.orderByDesc(DocVersion::getVersionNo);
        return list(wrapper);
    }

    @Override
    public DocVersion getLatestVersion(Long docId) {
        LambdaQueryWrapper<DocVersion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocVersion::getDocId, docId);
        wrapper.orderByDesc(DocVersion::getVersionNo);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }

    @Override
    @Transactional
    public int createNewVersion(DocVersion version) {
        // Get latest version number
        DocVersion latest = getLatestVersion(version.getDocId());
        long newVersionNo = (latest != null) ? latest.getVersionNo() + 1 : 1L;
        version.setVersionNo(newVersionNo);
        version.setCreateTime(LocalDateTime.now());
        return save(version) ? 1 : 0;
    }
}