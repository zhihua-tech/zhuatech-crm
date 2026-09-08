/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;
import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.QuotationApprovalGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/enterprise/crm")
public class QuotationApprovalGovernanceController {
    private final QuotationApprovalGovernanceService service;
    public QuotationApprovalGovernanceController(QuotationApprovalGovernanceService service) { this.service = service; }
    @PostMapping("/quotation-approval")
    public ApiResponse<QuotationApprovalGovernanceService.Assessment> assess(@Valid @RequestBody QuotationApprovalGovernanceService.Request request) {
        return ApiResponse.ok("报价审批评估完成", service.assess(request));
    }
}
