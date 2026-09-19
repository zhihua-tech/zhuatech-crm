/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;
import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.QuotationApprovalGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/crm")
public class QuotationApprovalGovernanceController {
    private final QuotationApprovalGovernanceService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public QuotationApprovalGovernanceController(QuotationApprovalGovernanceService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/quotation-approval")
    public ApiResponse<QuotationApprovalGovernanceService.Assessment> assess(@Valid @RequestBody QuotationApprovalGovernanceService.Request request) {
        return ApiResponse.ok("报价审批评估完成", service.assess(request));
    }
}
