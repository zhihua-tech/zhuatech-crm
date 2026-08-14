/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.LeadScoringService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crm/insights")
public class LeadScoringController {
    private final LeadScoringService service;
    public LeadScoringController(LeadScoringService service) { this.service = service; }

    @PostMapping("/lead-score")
    public ApiResponse<LeadScoringService.Result> score(@Valid @RequestBody LeadScoringService.Request request) {
        return ApiResponse.ok(service.score(request));
    }
}
