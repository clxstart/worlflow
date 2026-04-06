package com.clx.workflow.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 项目阶段对象 project_stage
 *
 * @author clx
 */
@TableName("project_stage")
public class ProjectStage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段ID
     */
    @TableId(type = IdType.AUTO)
    private Long stageId;

    /**
     * 项目ID
     */
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    /**
     * 阶段名称
     */
    @NotBlank(message = "阶段名称不能为空")
    private String stageName;

    /**
     * 阶段顺序
     */
    private Integer stageSort;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    // ========== Getter & Setter ==========

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getStageSort() {
        return stageSort;
    }

    public void setStageSort(Integer stageSort) {
        this.stageSort = stageSort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}