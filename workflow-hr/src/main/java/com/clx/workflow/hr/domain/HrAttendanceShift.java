package com.clx.workflow.hr.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * 考勤排班对象 hr_attendance_shift
 *
 * @author clx
 */
@TableName("hr_attendance_shift")
public class HrAttendanceShift extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 班次ID
     */
    @TableId(type = IdType.AUTO)
    private Long shiftId;

    /**
     * 班次名称
     */
    @NotBlank(message = "班次名称不能为空")
    private String shiftName;

    /**
     * 上班时间
     */
    @NotNull(message = "上班时间不能为空")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime workStartTime;

    /**
     * 下班时间
     */
    @NotNull(message = "下班时间不能为空")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime workEndTime;

    /**
     * 迟到阈值（分钟）
     */
    private Integer lateThreshold;

    /**
     * 早退阈值（分钟）
     */
    private Integer earlyThreshold;

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

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public LocalTime getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(LocalTime workStartTime) {
        this.workStartTime = workStartTime;
    }

    public LocalTime getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(LocalTime workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Integer getLateThreshold() {
        return lateThreshold;
    }

    public void setLateThreshold(Integer lateThreshold) {
        this.lateThreshold = lateThreshold;
    }

    public Integer getEarlyThreshold() {
        return earlyThreshold;
    }

    public void setEarlyThreshold(Integer earlyThreshold) {
        this.earlyThreshold = earlyThreshold;
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