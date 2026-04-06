package com.clx.workflow.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.hr.domain.HrAttendance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考勤记录 Mapper 接口
 *
 * @author clx
 */
@Mapper
public interface HrAttendanceMapper extends BaseMapper<HrAttendance> {
}