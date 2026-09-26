/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.CustomerCreditLimitGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@RestController
@RequestMapping("/api/enterprise/crm")
public class CustomerCreditLimitGovernanceController {
    private final CustomerCreditLimitGovernanceService service;

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public CustomerCreditLimitGovernanceController(CustomerCreditLimitGovernanceService service) {
        this.service = service;
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @PostMapping("/customer-credit-limit")
    public ApiResponse<CustomerCreditLimitGovernanceService.Assessment> assess(
            @Valid @RequestBody CustomerCreditLimitGovernanceService.Request request) {
        return ApiResponse.ok("客户信用额度评估完成", service.assess(request));
    }
}
