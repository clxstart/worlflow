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
 * 资产信息对象 asset_info
 *
 * @author clx
 */
@TableName("asset_info")
public class AssetInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long assetId;

    @NotBlank(message = "资产编号不能为空")
    private String assetNo;

    @NotBlank(message = "资产名称不能为空")
    private String assetName;

    @NotNull(message = "资产分类不能为空")
    private Long categoryId;

    private String categoryName;

    private String brand;

    private String model;

    private String spec;

    private String unit;

    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    private BigDecimal purchasePrice;

    private BigDecimal originalValue;

    private BigDecimal netValue;

    private BigDecimal depreciationValue;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyDate;

    private String supplier;

    private String location;

    private String status;

    private Long userId;

    private String userName;

    private Long deptId;

    private String deptName;

    private String image;

    // Getter & Setter
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getAssetNo() { return assetNo; }
    public void setAssetNo(String assetNo) { this.assetNo = assetNo; }
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
    public BigDecimal getOriginalValue() { return originalValue; }
    public void setOriginalValue(BigDecimal originalValue) { this.originalValue = originalValue; }
    public BigDecimal getNetValue() { return netValue; }
    public void setNetValue(BigDecimal netValue) { this.netValue = netValue; }
    public BigDecimal getDepreciationValue() { return depreciationValue; }
    public void setDepreciationValue(BigDecimal depreciationValue) { this.depreciationValue = depreciationValue; }
    public LocalDate getWarrantyDate() { return warrantyDate; }
    public void setWarrantyDate(LocalDate warrantyDate) { this.warrantyDate = warrantyDate; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}