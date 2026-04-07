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

/**
 * 资产维修对象 asset_repair
 *
 * @author clx
 */
@TableName("asset_repair")
public class AssetRepair extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long repairId;

    @NotBlank(message = "维修单号不能为空")
    private String repairNo;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    private String assetNo;

    private String assetName;

    @NotBlank(message = "故障描述不能为空")
    private String faultDesc;

    private String repairType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate repairDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate completeDate;

    private BigDecimal repairCost;

    private String repairCompany;

    private String repairResult;

    private String status;

    // Getter & Setter
    public Long getRepairId() { return repairId; }
    public void setRepairId(Long repairId) { this.repairId = repairId; }
    public String getRepairNo() { return repairNo; }
    public void setRepairNo(String repairNo) { this.repairNo = repairNo; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getAssetNo() { return assetNo; }
    public void setAssetNo(String assetNo) { this.assetNo = assetNo; }
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    public String getFaultDesc() { return faultDesc; }
    public void setFaultDesc(String faultDesc) { this.faultDesc = faultDesc; }
    public String getRepairType() { return repairType; }
    public void setRepairType(String repairType) { this.repairType = repairType; }
    public LocalDate getRepairDate() { return repairDate; }
    public void setRepairDate(LocalDate repairDate) { this.repairDate = repairDate; }
    public LocalDate getCompleteDate() { return completeDate; }
    public void setCompleteDate(LocalDate completeDate) { this.completeDate = completeDate; }
    public BigDecimal getRepairCost() { return repairCost; }
    public void setRepairCost(BigDecimal repairCost) { this.repairCost = repairCost; }
    public String getRepairCompany() { return repairCompany; }
    public void setRepairCompany(String repairCompany) { this.repairCompany = repairCompany; }
    public String getRepairResult() { return repairResult; }
    public void setRepairResult(String repairResult) { this.repairResult = repairResult; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}