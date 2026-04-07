package com.clx.workflow.asset.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 盘点明细对象 asset_inventory_detail
 *
 * @author clx
 */
@TableName("asset_inventory_detail")
public class AssetInventoryDetail {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long detailId;

    @NotNull(message = "盘点ID不能为空")
    private Long inventoryId;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    private String assetNo;

    private String assetName;

    private Integer bookQuantity;

    private Integer actualQuantity;

    private Integer diffQuantity;

    private String diffType;

    private String diffReason;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // Getter & Setter
    public Long getDetailId() { return detailId; }
    public void setDetailId(Long detailId) { this.detailId = detailId; }
    public Long getInventoryId() { return inventoryId; }
    public void setInventoryId(Long inventoryId) { this.inventoryId = inventoryId; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getAssetNo() { return assetNo; }
    public void setAssetNo(String assetNo) { this.assetNo = assetNo; }
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    public Integer getBookQuantity() { return bookQuantity; }
    public void setBookQuantity(Integer bookQuantity) { this.bookQuantity = bookQuantity; }
    public Integer getActualQuantity() { return actualQuantity; }
    public void setActualQuantity(Integer actualQuantity) { this.actualQuantity = actualQuantity; }
    public Integer getDiffQuantity() { return diffQuantity; }
    public void setDiffQuantity(Integer diffQuantity) { this.diffQuantity = diffQuantity; }
    public String getDiffType() { return diffType; }
    public void setDiffType(String diffType) { this.diffType = diffType; }
    public String getDiffReason() { return diffReason; }
    public void setDiffReason(String diffReason) { this.diffReason = diffReason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}