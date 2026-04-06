package com.clx.workflow.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.hr.domain.HrLeave;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假申请 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface HrLeaveMapper extends BaseMapper<HrLeave> {
}