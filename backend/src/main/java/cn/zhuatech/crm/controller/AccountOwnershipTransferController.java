/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.controller;

import cn.zhuatech.crm.common.ApiResponse;
import cn.zhuatech.crm.service.AccountOwnershipTransferService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/crm")
public class AccountOwnershipTransferController {
    private final AccountOwnershipTransferService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AccountOwnershipTransferController(AccountOwnershipTransferService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/account-ownership-transfer")
    public ApiResponse<AccountOwnershipTransferService.Assessment> assess(
            @Valid @RequestBody AccountOwnershipTransferService.Request request) {
        return ApiResponse.ok("客户归属转移评估完成", service.assess(request));
    }
}
