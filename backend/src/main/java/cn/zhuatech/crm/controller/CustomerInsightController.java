/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.CustomerHealthService;
import cn.zhuatech.crm.service.OpportunityForecastService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer-intelligence")
public class CustomerInsightController {
    private final CustomerHealthService service;
    private final OpportunityForecastService forecastService;
    public CustomerInsightController(CustomerHealthService service, OpportunityForecastService forecastService) {
        this.service = service;
        this.forecastService = forecastService;
    }

    @PostMapping("/health-score")
    public ApiResponse<CustomerHealthService.Result> evaluate(@Valid @RequestBody CustomerHealthService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }

    @PostMapping("/opportunity-forecast")
    public ApiResponse<OpportunityForecastService.ForecastResult> forecast(
        @Valid @RequestBody OpportunityForecastService.ForecastRequest request) {
        return ApiResponse.ok(forecastService.forecast(request));
    }
}
