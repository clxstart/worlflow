package com.clx.workflow.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.finance.domain.FinanceReimbursementDetail;
import com.clx.workflow.finance.mapper.FinanceReimbursementDetailMapper;
import com.clx.workflow.finance.service.IFinanceReimbursementDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报销明细 Service 实现类
 *
 * @author clx
 */
@Service
public class FinanceReimbursementDetailServiceImpl extends ServiceImpl<FinanceReimbursementDetailMapper, FinanceReimbursementDetail> implements IFinanceReimbursementDetailService {

    @Override
    public List<FinanceReimbursementDetail> selectDetailListByReimbursementId(Long reimbursementId) {
        LambdaQueryWrapper<FinanceReimbursementDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FinanceReimbursementDetail::getReimbursementId, reimbursementId);
        return baseMapper.selectList(wrapper);
    }
}