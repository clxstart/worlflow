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
 * 收入记录对象 finance_income
 *
 * @author clx
 */
@TableName("finance_income")
public class FinanceIncome extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 收入ID
     */
    @TableId(type = IdType.AUTO)
    private Long incomeId;

    /**
     * 收入编号
     */
    @NotBlank(message = "收入编号不能为空")
    private String incomeNo;

    /**
     * 收入类型
     */
    @NotBlank(message = "收入类型不能为空")
    private String incomeType;

    /**
     * 收入来源
     */
    private String incomeSource;

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
     * 收入日期
     */
    @NotNull(message = "收入日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate incomeDate;

    /**
     * 收款方式
     */
    private String paymentMethod;

    /**
     * 付款方
     */
    private String payer;

    /**
     * 关联合同ID
     */
    private Long contractId;

    /**
     * 关联项目ID
     */
    private Long projectId;

    /**
     * 开票状态（0未开票 1已开票）
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
     * 状态（0待确认 1已确认 2已取消）
     */
    private String status;

    // ========== Getter & Setter ==========

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public String getIncomeNo() {
        return incomeNo;
    }

    public void setIncomeNo(String incomeNo) {
        this.incomeNo = incomeNo;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
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

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}