/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.*;
import cn.zhuatech.crm.dto.CrmDto.*;
import cn.zhuatech.crm.model.Opportunity;
import cn.zhuatech.crm.repository.OpportunityRepository;
import cn.zhuatech.crm.service.CrmAccessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/opportunities")
public class OpportunityController {
    private final OpportunityRepository opportunities; private final CrmAccessService access;
    public OpportunityController(OpportunityRepository opportunities,CrmAccessService access){this.opportunities=opportunities;this.access=access;}
    @GetMapping public ApiResponse<List<OpportunityView>> list(@RequestParam(required=false) Long customerId){
        if(customerId!=null)return ApiResponse.ok(opportunities.findByCustomerOrderByUpdatedAtDesc(access.customer(customerId)).stream().map(OpportunityView::from).toList());
        var user=access.current();var items=access.canViewAll(user)?opportunities.findAllByOrderByUpdatedAtDesc():opportunities.findByOwnerOrderByUpdatedAtDesc(user);return ApiResponse.ok(items.stream().map(OpportunityView::from).toList());
    }
    @PostMapping public ApiResponse<OpportunityView> create(@Valid @RequestBody OpportunityRequest r){var customer=access.customer(r.customerId());var item=new Opportunity(customer,access.current(),r.name(),r.amount(),r.stage(),r.probability(),r.expectedCloseDate(),r.nextStep());return ApiResponse.ok("商机已创建",OpportunityView.from(opportunities.save(item)));}
    @PatchMapping("/{id}/stage") public ApiResponse<OpportunityView> stage(@PathVariable Long id,@Valid @RequestBody OpportunityStageRequest r){var item=opportunities.findById(id).orElseThrow(()->new BusinessException("商机不存在"));access.customer(item.getCustomer().getId());item.changeStage(r.stage(),r.probability(),r.nextStep());return ApiResponse.ok("商机阶段已更新",OpportunityView.from(opportunities.save(item)));}
}
