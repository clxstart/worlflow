package com.clx.workflow.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 预算对象 finance_budget
 *
 * @author clx
 */
@TableName("finance_budget")
public class FinanceBudget extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预算ID
     */
    @TableId(type = IdType.AUTO)
    private Long budgetId;

    /**
     * 预算编号
     */
    @NotBlank(message = "预算编号不能为空")
    private String budgetNo;

    /**
     * 预算名称
     */
    @NotBlank(message = "预算名称不能为空")
    private String budgetName;

    /**
     * 预算类型
     */
    @NotBlank(message = "预算类型不能为空")
    private String budgetType;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 预算年度
     */
    @NotNull(message = "预算年度不能为空")
    private Integer budgetYear;

    /**
     * 预算月份（年度预算为空）
     */
    private Integer budgetMonth;

    /**
     * 预算总额
     */
    @NotNull(message = "预算总额不能为空")
    private BigDecimal totalAmount;

    /**
     * 已使用金额
     */
    private BigDecimal usedAmount;

    /**
     * 剩余金额
     */
    private BigDecimal remainingAmount;

    /**
     * 冻结金额
     */
    private BigDecimal freezeAmount;

    /**
     * 状态（0草稿 1已生效 2已失效）
     */
    private String status;

    /**
     * 审批人ID
     */
    private Long approveUserId;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime approveTime;

    // ========== Getter & Setter ==========

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetNo() {
        return budgetNo;
    }

    public void setBudgetNo(String budgetNo) {
        this.budgetNo = budgetNo;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(Integer budgetYear) {
        this.budgetYear = budgetYear;
    }

    public Integer getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(Integer budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public java.time.LocalDateTime getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(java.time.LocalDateTime approveTime) {
        this.approveTime = approveTime;
    }
}