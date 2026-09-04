/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.CustomerAccountMergeGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/crm")
public class CustomerAccountMergeGovernanceController {
    private final CustomerAccountMergeGovernanceService service;
    public CustomerAccountMergeGovernanceController(CustomerAccountMergeGovernanceService service) { this.service = service; }

    @PostMapping("/customer-account-merge")
    public ApiResponse<CustomerAccountMergeGovernanceService.Assessment> assess(
            @Valid @RequestBody CustomerAccountMergeGovernanceService.Request request) {
        return ApiResponse.ok("客户主数据合并评估完成", service.assess(request));
    }
}
