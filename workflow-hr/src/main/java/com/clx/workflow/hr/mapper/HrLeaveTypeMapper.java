package com.clx.workflow.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.hr.domain.HrLeaveType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假类型 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface HrLeaveTypeMapper extends BaseMapper<HrLeaveType> {
}