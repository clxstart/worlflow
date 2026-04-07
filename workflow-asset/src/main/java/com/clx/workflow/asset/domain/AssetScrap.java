package com.clx.workflow.asset.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 资产报废对象 asset_scrap
 *
 * @author clx
 */
@TableName("asset_scrap")
public class AssetScrap extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long scrapId;

    @NotBlank(message = "报废单号不能为空")
    private String scrapNo;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    private String assetNo;

    private String assetName;

    private BigDecimal originalValue;

    private BigDecimal netValue;

    @NotBlank(message = "报废原因不能为空")
    private String scrapReason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate scrapDate;

    private BigDecimal scrapValue;

    private Long userId;

    private String userName;

    private String status;

    private Long approveUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approveTime;

    // Getter & Setter
    public Long getScrapId() { return scrapId; }
    public void setScrapId(Long scrapId) { this.scrapId = scrapId; }
    public String getScrapNo() { return scrapNo; }
    public void setScrapNo(String scrapNo) { this.scrapNo = scrapNo; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getAssetNo() { return assetNo; }
    public void setAssetNo(String assetNo) { this.assetNo = assetNo; }
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    public BigDecimal getOriginalValue() { return originalValue; }
    public void setOriginalValue(BigDecimal originalValue) { this.originalValue = originalValue; }
    public BigDecimal getNetValue() { return netValue; }
    public void setNetValue(BigDecimal netValue) { this.netValue = netValue; }
    public String getScrapReason() { return scrapReason; }
    public void setScrapReason(String scrapReason) { this.scrapReason = scrapReason; }
    public LocalDate getScrapDate() { return scrapDate; }
    public void setScrapDate(LocalDate scrapDate) { this.scrapDate = scrapDate; }
    public BigDecimal getScrapValue() { return scrapValue; }
    public void setScrapValue(BigDecimal scrapValue) { this.scrapValue = scrapValue; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getApproveUserId() { return approveUserId; }
    public void setApproveUserId(Long approveUserId) { this.approveUserId = approveUserId; }
    public LocalDateTime getApproveTime() { return approveTime; }
    public void setApproveTime(LocalDateTime approveTime) { this.approveTime = approveTime; }
}