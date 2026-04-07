package com.clx.workflow.doc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 文档分类对象 doc_category
 *
 * @author clx
 */
@TableName("doc_category")
public class DocCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long categoryId;

    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    private Long parentId;

    private String ancestors;

    private String categoryCode;

    private Integer sort;

    private String categoryType;

    private String status;

    @TableField(exist = false)
    private List<DocCategory> children;

    // Getter & Setter
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getAncestors() { return ancestors; }
    public void setAncestors(String ancestors) { this.ancestors = ancestors; }
    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public String getCategoryType() { return categoryType; }
    public void setCategoryType(String categoryType) { this.categoryType = categoryType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<DocCategory> getChildren() { return children; }
    public void setChildren(List<DocCategory> children) { this.children = children; }
}