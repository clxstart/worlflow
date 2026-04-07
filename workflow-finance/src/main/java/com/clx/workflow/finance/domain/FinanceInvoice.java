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
 * 发票管理对象 finance_invoice
 *
 * @author clx
 */
@TableName("finance_invoice")
public class FinanceInvoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    @TableId(type = IdType.AUTO)
    private Long invoiceId;

    /**
     * 发票号码
     */
    @NotBlank(message = "发票号码不能为空")
    private String invoiceNo;

    /**
     * 发票类型（增值税专用发票、增值税普通发票、电子发票等）
     */
    @NotBlank(message = "发票类型不能为空")
    private String invoiceType;

    /**
     * 发票类别（1进项 2销项）
     */
    @NotBlank(message = "发票类别不能为空")
    private String invoiceCategory;

    /**
     * 发票金额
     */
    @NotNull(message = "发票金额不能为空")
    private BigDecimal amount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 价税合计
     */
    @NotNull(message = "价税合计不能为空")
    private BigDecimal totalAmount;

    /**
     * 开票公司
     */
    @NotBlank(message = "开票公司不能为空")
    private String companyName;

    /**
     * 纳税人识别号
     */
    private String taxNo;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 公司电话
     */
    private String companyPhone;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 开票日期
     */
    @NotNull(message = "开票日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;

    /**
     * 查验状态（0未查验 1已查验 2查验失败）
     */
    private String verifyStatus;

    /**
     * 查验时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime verifyTime;

    /**
     * 发票文件路径
     */
    private String filePath;

    /**
     * 状态（0有效 1作废 2红冲）
     */
    private String status;

    // ========== Getter & Setter ==========

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(String invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public java.time.LocalDateTime getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(java.time.LocalDateTime verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}