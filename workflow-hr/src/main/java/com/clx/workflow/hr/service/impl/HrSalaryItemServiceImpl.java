package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrSalaryItem;
import com.clx.workflow.hr.mapper.HrSalaryItemMapper;
import com.clx.workflow.hr.service.IHrSalaryItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 薪资项 Service 实现类
 *
 * @author clx
 */
@Service
public class HrSalaryItemServiceImpl extends ServiceImpl<HrSalaryItemMapper, HrSalaryItem> implements IHrSalaryItemService {

    @Override
    public List<HrSalaryItem> selectSalaryItemsBySalaryId(Long salaryId) {
        LambdaQueryWrapper<HrSalaryItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HrSalaryItem::getSalaryId, salaryId);
        wrapper.orderByAsc(HrSalaryItem::getItemId);
        return baseMapper.selectList(wrapper);
    }
}