package com.clx.workflow.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.flowable.domain.WfTodoTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 待办任务 Mapper
 *
 * @author clx
 */
@Mapper
public interface WfTodoTaskMapper extends BaseMapper<WfTodoTask> {
}