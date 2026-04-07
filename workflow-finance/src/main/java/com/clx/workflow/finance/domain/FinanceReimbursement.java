package com.clx.workflow.finance.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 报销申请对象 finance_reimbursement
 *
 * @author clx
 */
@TableName("finance_reimbursement")
public class FinanceReimbursement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 报销ID
     */
    @TableId(type = IdType.AUTO)
    private Long reimbursementId;

    /**
     * 报销编号
     */
    @NotBlank(message = "报销编号不能为空")
    private String reimbursementNo;

    /**
     * 申请人ID
     */
    @NotNull(message = "申请人ID不能为空")
    private Long userId;

    /**
     * 申请人姓名
     */
    private String userName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 报销类型
     */
    @NotBlank(message = "报销类型不能为空")
    private String reimbursementType;

    /**
     * 报销金额
     */
    @NotNull(message = "报销金额不能为空")
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 费用发生日期
     */
    @NotNull(message = "费用发生日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    /**
     * 报销说明
     */
    private String description;

    /**
     * 附件
     */
    private String attachment;

    /**
     * 关联项目ID
     */
    private Long projectId;

    /**
     * 当前审批人ID
     */
    private Long approveUserId;

    /**
     * 审批状态（0待审批 1审批中 2已通过 3已拒绝 4已撤销）
     */
    private String approveStatus;

    /**
     * 最后审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime approveTime;

    /**
     * 支付状态（0未支付 1已支付）
     */
    private String payStatus;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime payTime;

    // ========== Getter & Setter ==========

    public Long getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(Long reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public String getReimbursementNo() {
        return reimbursementNo;
    }

    public void setReimbursementNo(String reimbursementNo) {
        this.reimbursementNo = reimbursementNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(String reimbursementType) {
        this.reimbursementType = reimbursementType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public java.time.LocalDateTime getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(java.time.LocalDateTime approveTime) {
        this.approveTime = approveTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public java.time.LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(java.time.LocalDateTime payTime) {
        this.payTime = payTime;
    }
}