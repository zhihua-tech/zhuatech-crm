/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.LeadConversionGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/crm")
public class LeadConversionGovernanceController {
    private final LeadConversionGovernanceService service;

    public LeadConversionGovernanceController(LeadConversionGovernanceService service) {
        this.service = service;
    }

    @PostMapping("/lead-conversion")
    public ApiResponse<LeadConversionGovernanceService.Assessment> assess(
            @Valid @RequestBody LeadConversionGovernanceService.Request request) {
        return ApiResponse.ok("线索转客户治理评估完成", service.assess(request));
    }
}
