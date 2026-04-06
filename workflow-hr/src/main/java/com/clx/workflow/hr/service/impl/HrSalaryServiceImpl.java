package com.clx.workflow.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.hr.domain.HrSalary;
import com.clx.workflow.hr.mapper.HrSalaryMapper;
import com.clx.workflow.hr.service.IHrSalaryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 薪资 Service 实现类
 *
 * @author clx
 */
@Service
public class HrSalaryServiceImpl extends ServiceImpl<HrSalaryMapper, HrSalary> implements IHrSalaryService {

    @Override
    public List<HrSalary> selectSalaryList(HrSalary salary) {
        LambdaQueryWrapper<HrSalary> wrapper = new LambdaQueryWrapper<>();
        if (salary.getUserId() != null) {
            wrapper.eq(HrSalary::getUserId, salary.getUserId());
        }
        if (salary.getUserName() != null && !salary.getUserName().isEmpty()) {
            wrapper.like(HrSalary::getUserName, salary.getUserName());
        }
        if (salary.getDeptId() != null) {
            wrapper.eq(HrSalary::getDeptId, salary.getDeptId());
        }
        if (salary.getSalaryMonth() != null && !salary.getSalaryMonth().isEmpty()) {
            wrapper.eq(HrSalary::getSalaryMonth, salary.getSalaryMonth());
        }
        if (salary.getStatus() != null && !salary.getStatus().isEmpty()) {
            wrapper.eq(HrSalary::getStatus, salary.getStatus());
        }
        wrapper.orderByDesc(HrSalary::getSalaryMonth);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public HrSalary selectSalaryByUserIdAndMonth(Long userId, String salaryMonth) {
        LambdaQueryWrapper<HrSalary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HrSalary::getUserId, userId);
        wrapper.eq(HrSalary::getSalaryMonth, salaryMonth);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 计算实发工资
     */
    public BigDecimal calculateActualSalary(HrSalary salary) {
        BigDecimal total = BigDecimal.ZERO;
        // 增项
        if (salary.getBaseSalary() != null) total = total.add(salary.getBaseSalary());
        if (salary.getPostSalary() != null) total = total.add(salary.getPostSalary());
        if (salary.getPerformanceSalary() != null) total = total.add(salary.getPerformanceSalary());
        if (salary.getOvertimeSalary() != null) total = total.add(salary.getOvertimeSalary());
        if (salary.getBonus() != null) total = total.add(salary.getBonus());
        if (salary.getSubsidy() != null) total = total.add(salary.getSubsidy());
        // 扣项
        if (salary.getDeduction() != null) total = total.subtract(salary.getDeduction());
        if (salary.getTax() != null) total = total.subtract(salary.getTax());
        if (salary.getSocialInsurance() != null) total = total.subtract(salary.getSocialInsurance());
        if (salary.getHousingFund() != null) total = total.subtract(salary.getHousingFund());
        return total;
    }
}