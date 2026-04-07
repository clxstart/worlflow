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
 * 支出记录对象 finance_expense
 *
 * @author clx
 */
@TableName("finance_expense")
public class FinanceExpense extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 支出ID
     */
    @TableId(type = IdType.AUTO)
    private Long expenseId;

    /**
     * 支出编号
     */
    @NotBlank(message = "支出编号不能为空")
    private String expenseNo;

    /**
     * 支出类型
     */
    @NotBlank(message = "支出类型不能为空")
    private String expenseType;

    /**
     * 支出分类
     */
    private String expenseCategory;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 支出日期
     */
    @NotNull(message = "支出日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    /**
     * 付款方式
     */
    private String paymentMethod;

    /**
     * 收款方
     */
    private String payee;

    /**
     * 收款账户
     */
    private String bankAccount;

    /**
     * 关联合同ID
     */
    private Long contractId;

    /**
     * 关联项目ID
     */
    private Long projectId;

    /**
     * 收票状态（0未收票 1已收票）
     */
    private String invoiceStatus;

    /**
     * 发票ID
     */
    private Long invoiceId;

    /**
     * 经办人ID
     */
    private Long handlerId;

    /**
     * 经办人姓名
     */
    private String handlerName;

    /**
     * 审批人ID
     */
    private Long approveUserId;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime approveTime;

    /**
     * 状态（0待审批 1已审批 2已支付 3已取消）
     */
    private String status;

    // ========== Getter & Setter ==========

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseNo() {
        return expenseNo;
    }

    public void setExpenseNo(String expenseNo) {
        this.expenseNo = expenseNo;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}