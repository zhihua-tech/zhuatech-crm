/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class AccountOwnershipTransferServiceTest {
    private final AccountOwnershipTransferService service = new AccountOwnershipTransferService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void transfersOrdinaryAccountWithCompleteHandover() {
        var result = service.assess(request(false, true, 12, 50, 2, true, true));
        assertThat(result.decision()).isEqualTo(AccountOwnershipTransferService.Decision.TRANSFER);
        assertThat(result.blockers()).isEmpty();
        assertThat(result.actions()).isEmpty();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void reviewsTransferWhenOpportunityHandoverIsIncomplete() {
        var result = service.assess(request(false, true, 12, 50, 3, false, false));
        assertThat(result.decision()).isEqualTo(AccountOwnershipTransferService.Decision.REVIEW);
        assertThat(result.actions()).anyMatch(action -> action.contains("商机"));
        assertThat(result.actions()).anyMatch(action -> action.contains("通知"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksProtectedAccountAndCapacityViolation() {
        var result = service.assess(request(true, false, 50, 50, 0, true, true));
        assertThat(result.decision()).isEqualTo(AccountOwnershipTransferService.Decision.BLOCKED);
        assertThat(result.blockers()).anyMatch(item -> item.contains("容量"));
        assertThat(result.blockers()).anyMatch(item -> item.contains("保护客户"));
        assertThat(result.approvalRoute()).contains("销售总监");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private AccountOwnershipTransferService.Request request(boolean protectedAccount,
                                                             boolean executiveApproved,
                                                             int accountCount, int capacity,
                                                             int opportunities,
                                                             boolean handoverReady,
                                                             boolean notificationPlanned) {
        return new AccountOwnershipTransferService.Request("TR-100", "ACC-100", "sales-1", "sales-2",
                true, true, false, true, false, protectedAccount, executiveApproved, true,
                accountCount, capacity, opportunities, handoverReady, notificationPlanned, true);
    }
}
