/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.*;
import cn.zhuatech.crm.dto.CrmDto.*;
import cn.zhuatech.crm.model.SalesTask;
import cn.zhuatech.crm.repository.SalesTaskRepository;
import cn.zhuatech.crm.service.CrmAccessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/tasks")
public class SalesTaskController {
    private final SalesTaskRepository tasks; private final CrmAccessService access;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public SalesTaskController(SalesTaskRepository tasks,CrmAccessService access){this.tasks=tasks;this.access=access;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping public ApiResponse<List<TaskView>> list(){return ApiResponse.ok(tasks.findByAssigneeOrderByCompletedAscDueDateAsc(access.current()).stream().map(TaskView::from).toList());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping public ApiResponse<TaskView> create(@Valid @RequestBody TaskRequest r){var customer=r.customerId()==null?null:access.customer(r.customerId());var task=new SalesTask(access.current(),customer,r.title(),r.description(),r.dueDate(),r.priority()==null?"MEDIUM":r.priority());return ApiResponse.ok("销售任务已创建",TaskView.from(tasks.save(task)));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PatchMapping("/{id}") public ApiResponse<TaskView> status(@PathVariable Long id,@RequestBody TaskStatusRequest r){SalesTask task=tasks.findByIdAndAssignee(id,access.current()).orElseThrow(()->new BusinessException("任务不存在或无权操作"));task.setCompleted(r.completed());return ApiResponse.ok(TaskView.from(tasks.save(task)));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @DeleteMapping("/{id}") public ApiResponse<Void> delete(@PathVariable Long id){SalesTask task=tasks.findByIdAndAssignee(id,access.current()).orElseThrow(()->new BusinessException("任务不存在或无权操作"));tasks.delete(task);return ApiResponse.ok("任务已删除",null);}
}
