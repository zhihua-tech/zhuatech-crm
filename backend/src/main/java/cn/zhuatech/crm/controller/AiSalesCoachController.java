/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.AiSalesCoachService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crm/ai")
public class AiSalesCoachController {
    private final AiSalesCoachService service;
    public AiSalesCoachController(AiSalesCoachService service) { this.service = service; }
    @PostMapping("/sales-coach")
    public ApiResponse<AiSalesCoachService.Result> coach(@Valid @RequestBody AiSalesCoachService.Request request) {
        return ApiResponse.ok(service.coach(request));
    }
}
