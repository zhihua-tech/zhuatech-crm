/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.NextBestActionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crm/insights")
public class NextBestActionController {
    private final NextBestActionService service;
    public NextBestActionController(NextBestActionService service) { this.service = service; }

    @PostMapping("/next-best-action")
    public ApiResponse<NextBestActionService.Result> recommend(@Valid @RequestBody NextBestActionService.Request request) {
        return ApiResponse.ok(service.recommend(request));
    }
}
