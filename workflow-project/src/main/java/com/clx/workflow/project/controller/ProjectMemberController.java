package com.clx.workflow.project.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.project.domain.ProjectMember;
import com.clx.workflow.project.service.IProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目成员 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/project/member")
public class ProjectMemberController extends BaseController {

    @Autowired
    private IProjectMemberService memberService;

    /**
     * 根据项目ID查询成员列表
     */
    @GetMapping("/list/{projectId}")
    public AjaxResult list(@PathVariable Long projectId) {
        List<ProjectMember> list = memberService.selectMembersByProjectId(projectId);
        return success(list);
    }

    /**
     * 查询成员详情
     */
    @GetMapping("/{memberId}")
    public AjaxResult getInfo(@PathVariable Long memberId) {
        return success(memberService.getById(memberId));
    }

    /**
     * 新增成员
     */
    @PreAuthorize("@ss.hasPermi('project:member:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ProjectMember member) {
        member.setJoinTime(LocalDateTime.now());
        return toAjax(memberService.save(member));
    }

    /**
     * 修改成员
     */
    @PreAuthorize("@ss.hasPermi('project:member:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ProjectMember member) {
        return toAjax(memberService.updateById(member));
    }

    /**
     * 删除成员
     */
    @PreAuthorize("@ss.hasPermi('project:member:remove')")
    @DeleteMapping("/{memberIds}")
    public AjaxResult remove(@PathVariable Long[] memberIds) {
        return toAjax(memberService.removeByIds(java.util.Arrays.asList(memberIds)));
    }
}