package com.clx.workflow.hr.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 请假类型对象 hr_leave_type
 *
 * @author clx
 */
@TableName("hr_leave_type")
public class HrLeaveType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 类型ID
     */
    @TableId(type = IdType.AUTO)
    private Long typeId;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    private String typeName;

    /**
     * 类型编码
     */
    @NotBlank(message = "类型编码不能为空")
    private String typeCode;

    /**
     * 年假天数
     */
    private Integer annualDays;

    /**
     * 状态（0正常 1停用）
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getAnnualDays() {
        return annualDays;
    }

    public void setAnnualDays(Integer annualDays) {
        this.annualDays = annualDays;
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