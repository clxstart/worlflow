package com.clx.workflow.asset.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 资产分类对象 asset_category
 *
 * @author clx
 */
@TableName("asset_category")
public class AssetCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long categoryId;

    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    private Long parentId;

    private String ancestors;

    private String categoryCode;

    private Integer sort;

    private String depreciationMethod;

    private Integer depreciationYears;

    private String status;

    @TableField(exist = false)
    private List<AssetCategory> children;

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
    public String getDepreciationMethod() { return depreciationMethod; }
    public void setDepreciationMethod(String depreciationMethod) { this.depreciationMethod = depreciationMethod; }
    public Integer getDepreciationYears() { return depreciationYears; }
    public void setDepreciationYears(Integer depreciationYears) { this.depreciationYears = depreciationYears; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<AssetCategory> getChildren() { return children; }
    public void setChildren(List<AssetCategory> children) { this.children = children; }
}