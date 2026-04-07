package com.clx.workflow.asset.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 资产盘点对象 asset_inventory
 *
 * @author clx
 */
@TableName("asset_inventory")
public class AssetInventory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long inventoryId;

    @NotBlank(message = "盘点单号不能为空")
    private String inventoryNo;

    @NotBlank(message = "盘点名称不能为空")
    private String inventoryName;

    private String inventoryType;

    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Integer totalCount;

    private Integer actualCount;

    private Integer profitCount;

    private Integer lossCount;

    private Long operatorId;

    private String operatorName;

    private String status;

    @TableField(exist = false)
    private List<AssetInventoryDetail> detailList;

    // Getter & Setter
    public Long getInventoryId() { return inventoryId; }
    public void setInventoryId(Long inventoryId) { this.inventoryId = inventoryId; }
    public String getInventoryNo() { return inventoryNo; }
    public void setInventoryNo(String inventoryNo) { this.inventoryNo = inventoryNo; }
    public String getInventoryName() { return inventoryName; }
    public void setInventoryName(String inventoryName) { this.inventoryName = inventoryName; }
    public String getInventoryType() { return inventoryType; }
    public void setInventoryType(String inventoryType) { this.inventoryType = inventoryType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getTotalCount() { return totalCount; }
    public void setTotalCount(Integer totalCount) { this.totalCount = totalCount; }
    public Integer getActualCount() { return actualCount; }
    public void setActualCount(Integer actualCount) { this.actualCount = actualCount; }
    public Integer getProfitCount() { return profitCount; }
    public void setProfitCount(Integer profitCount) { this.profitCount = profitCount; }
    public Integer getLossCount() { return lossCount; }
    public void setLossCount(Integer lossCount) { this.lossCount = lossCount; }
    public Long getOperatorId() { return operatorId; }
    public void setOperatorId(Long operatorId) { this.operatorId = operatorId; }
    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<AssetInventoryDetail> getDetailList() { return detailList; }
    public void setDetailList(List<AssetInventoryDetail> detailList) { this.detailList = detailList; }
}