package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceReimbursement;
import com.clx.workflow.finance.domain.FinanceReimbursementDetail;
import com.clx.workflow.finance.mapper.FinanceReimbursementMapper;
import com.clx.workflow.finance.service.IFinanceReimbursementDetailService;
import com.clx.workflow.finance.service.IFinanceReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报销申请 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceReimbursementServiceImpl extends ServiceImpl<FinanceReimbursementMapper, FinanceReimbursement> implements IFinanceReimbursementService {

    @Autowired
    private IFinanceReimbursementDetailService detailService;

    @Override
    public List<FinanceReimbursement> selectReimbursementList(FinanceReimbursement reimbursement) {
        LambdaQueryWrapper<FinanceReimbursement> wrapper = new LambdaQueryWrapper<>();
        if (reimbursement.getReimbursementNo() != null && !reimbursement.getReimbursementNo().isEmpty()) {
            wrapper.like(FinanceReimbursement::getReimbursementNo, reimbursement.getReimbursementNo());
        }
        if (reimbursement.getUserId() != null) {
            wrapper.eq(FinanceReimbursement::getUserId, reimbursement.getUserId());
        }
        if (reimbursement.getUserName() != null && !reimbursement.getUserName().isEmpty()) {
            wrapper.like(FinanceReimbursement::getUserName, reimbursement.getUserName());
        }
        if (reimbursement.getDeptId() != null) {
            wrapper.eq(FinanceReimbursement::getDeptId, reimbursement.getDeptId());
        }
        if (reimbursement.getReimbursementType() != null && !reimbursement.getReimbursementType().isEmpty()) {
            wrapper.eq(FinanceReimbursement::getReimbursementType, reimbursement.getReimbursementType());
        }
        if (reimbursement.getApproveStatus() != null && !reimbursement.getApproveStatus().isEmpty()) {
            wrapper.eq(FinanceReimbursement::getApproveStatus, reimbursement.getApproveStatus());
        }
        if (reimbursement.getPayStatus() != null && !reimbursement.getPayStatus().isEmpty()) {
            wrapper.eq(FinanceReimbursement::getPayStatus, reimbursement.getPayStatus());
        }
        wrapper.orderByDesc(FinanceReimbursement::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FinanceReimbursementDetail> selectDetailList(Long reimbursementId) {
        return detailService.selectDetailListByReimbursementId(reimbursementId);
    }

    @Override
    @Transactional
    public boolean submitReimbursement(FinanceReimbursement reimbursement, List<FinanceReimbursementDetail> details) {
        // 保存报销申请
        reimbursement.setApproveStatus("0"); // 待审批
        reimbursement.setPayStatus("0"); // 未支付
        boolean result = save(reimbursement);
        if (result && details != null && !details.isEmpty()) {
            // 保存报销明细
            for (FinanceReimbursementDetail detail : details) {
                detail.setReimbursementId(reimbursement.getReimbursementId());
                detail.setCreateTime(java.time.LocalDateTime.now());
            }
            detailService.saveBatch(details);
        }
        return result;
    }

    @Override
    public boolean approveReimbursement(Long reimbursementId, String approveStatus) {
        LambdaUpdateWrapper<FinanceReimbursement> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FinanceReimbursement::getReimbursementId, reimbursementId);
        wrapper.set(FinanceReimbursement::getApproveStatus, approveStatus);
        wrapper.set(FinanceReimbursement::getApproveTime, java.time.LocalDateTime.now());
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public boolean payReimbursement(Long reimbursementId) {
        LambdaUpdateWrapper<FinanceReimbursement> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(FinanceReimbursement::getReimbursementId, reimbursementId);
        wrapper.eq(FinanceReimbursement::getApproveStatus, "2"); // 已通过
        wrapper.set(FinanceReimbursement::getPayStatus, "1"); // 已支付
        wrapper.set(FinanceReimbursement::getPayTime, java.time.LocalDateTime.now());
        return baseMapper.update(null, wrapper) > 0;
    }
}