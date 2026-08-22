/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.*;
import cn.zhuatech.crm.dto.CrmDto.*;
import cn.zhuatech.crm.model.*;
import cn.zhuatech.crm.repository.*;
import cn.zhuatech.crm.service.CrmAccessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController @RequestMapping("/api/follow-ups")
public class FollowUpController {
    private final FollowUpRepository followUps; private final OpportunityRepository opportunities; private final CustomerRepository customers; private final CrmAccessService access;
    public FollowUpController(FollowUpRepository followUps,OpportunityRepository opportunities,CustomerRepository customers,CrmAccessService access){this.followUps=followUps;this.opportunities=opportunities;this.customers=customers;this.access=access;}
    @GetMapping public ApiResponse<List<FollowUpView>> list(@RequestParam Long customerId){return ApiResponse.ok(followUps.findByCustomerOrderByFollowUpAtDesc(access.customer(customerId)).stream().map(FollowUpView::from).toList());}
    @GetMapping("/recent") public ApiResponse<List<FollowUpView>> recent(){return ApiResponse.ok(followUps.findTop10ByCreatorOrderByFollowUpAtDesc(access.current()).stream().map(FollowUpView::from).toList());}
    @PostMapping public ApiResponse<FollowUpView> create(@Valid @RequestBody FollowUpRequest r){Customer customer=access.customer(r.customerId());Opportunity opportunity=null;if(r.opportunityId()!=null){opportunity=opportunities.findById(r.opportunityId()).orElseThrow(()->new BusinessException("商机不存在"));if(!opportunity.getCustomer().getId().equals(customer.getId()))throw new BusinessException("商机不属于该客户");}var item=new FollowUp(customer,opportunity,access.current(),r.method(),r.content(),r.followUpAt()==null?LocalDateTime.now():r.followUpAt(),r.nextAction(),r.nextFollowUpDate());customer.setNextFollowUpDate(r.nextFollowUpDate());customers.save(customer);return ApiResponse.ok("跟进记录已保存",FollowUpView.from(followUps.save(item)));}
}
