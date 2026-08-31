/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.OpportunityStageGateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/crm")
public class OpportunityStageGateController {
    private final OpportunityStageGateService service;
    public OpportunityStageGateController(OpportunityStageGateService service) { this.service = service; }

    @PostMapping("/opportunity-stage-gate")
    public ApiResponse<OpportunityStageGateService.Assessment> assess(
            @Valid @RequestBody OpportunityStageGateService.Request request) {
        return ApiResponse.ok("商机阶段门禁评估完成", service.assess(request));
    }
}
