/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class CustomerAccountMergeGovernanceServiceTest {
    private final CustomerAccountMergeGovernanceService service = new CustomerAccountMergeGovernanceService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void mergesGovernedDuplicateAccount() {
        var result = service.assess(request(true, true, true));
        assertEquals(CustomerAccountMergeGovernanceService.Decision.MERGE, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void reviewsMergeWithOperationalActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(CustomerAccountMergeGovernanceService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksUnsafeCustomerMerge() {
        var result = service.assess(new CustomerAccountMergeGovernanceService.Request("MERGE-003", "ACC-01", "ACC-02",
                false, false, false, false, false, false, false, false, false, false, false, false, false,
                true, true, true));
        assertEquals(CustomerAccountMergeGovernanceService.Decision.BLOCKED, result.decision());
        assertEquals(13, result.blockers().size());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksSelfMerge() {
        var request = request(true, true, true);
        var result = service.assess(new CustomerAccountMergeGovernanceService.Request(request.mergeRequestId(), "ACC-01", "ACC-01",
                true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true));
        assertEquals(CustomerAccountMergeGovernanceService.Decision.BLOCKED, result.decision());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private CustomerAccountMergeGovernanceService.Request request(boolean evidence, boolean sync, boolean notice) {
        return new CustomerAccountMergeGovernanceService.Request("MERGE-001", "ACC-01", "ACC-02",
                true, true, true, true, true, true, true, true, true, true, true, true, true,
                evidence, sync, notice);
    }
}
