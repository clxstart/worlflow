package com.clx.workflow.doc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clx.workflow.doc.domain.DocVersion;

import java.util.List;

/**
 * 文档版本Service接口
 */
public interface IDocVersionService extends IService<DocVersion> {

    List<DocVersion> selectVersionsByDocId(Long docId);

    DocVersion getLatestVersion(Long docId);

    int createNewVersion(DocVersion version);
}