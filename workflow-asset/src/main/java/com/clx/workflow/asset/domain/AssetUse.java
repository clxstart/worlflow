package com.clx.workflow.asset.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clx.workflow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 资产领用对象 asset_use
 *
 * @author clx
 */
@TableName("asset_use")
public class AssetUse extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long useId;

    @NotBlank(message = "领用单号不能为空")
    private String useNo;

    @NotNull(message = "资产ID不能为空")
    private Long assetId;

    private String assetNo;

    private String assetName;

    private Long userId;

    private String userName;

    private Long deptId;

    private String deptName;

    @NotNull(message = "领用日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate useDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectReturnDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate actualReturnDate;

    private String returnStatus;

    private String returnRemark;

    private String purpose;

    private String status;

    private Long approveUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approveTime;

    // Getter & Setter
    public Long getUseId() { return useId; }
    public void setUseId(Long useId) { this.useId = useId; }
    public String getUseNo() { return useNo; }
    public void setUseNo(String useNo) { this.useNo = useNo; }
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }
    public String getAssetNo() { return assetNo; }
    public void setAssetNo(String assetNo) { this.assetNo = assetNo; }
    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public LocalDate getUseDate() { return useDate; }
    public void setUseDate(LocalDate useDate) { this.useDate = useDate; }
    public LocalDate getExpectReturnDate() { return expectReturnDate; }
    public void setExpectReturnDate(LocalDate expectReturnDate) { this.expectReturnDate = expectReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDate actualReturnDate) { this.actualReturnDate = actualReturnDate; }
    public String getReturnStatus() { return returnStatus; }
    public void setReturnStatus(String returnStatus) { this.returnStatus = returnStatus; }
    public String getReturnRemark() { return returnRemark; }
    public void setReturnRemark(String returnRemark) { this.returnRemark = returnRemark; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getApproveUserId() { return approveUserId; }
    public void setApproveUserId(Long approveUserId) { this.approveUserId = approveUserId; }
    public LocalDateTime getApproveTime() { return approveTime; }
    public void setApproveTime(LocalDateTime approveTime) { this.approveTime = approveTime; }
}