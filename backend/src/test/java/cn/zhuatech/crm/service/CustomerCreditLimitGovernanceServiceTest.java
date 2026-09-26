/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class CustomerCreditLimitGovernanceServiceTest {
    private final CustomerCreditLimitGovernanceService service = new CustomerCreditLimitGovernanceService();

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void approvesControlledCreditIncrease() {
        var result = service.assess(request("500000", 0, false, true, true));
        assertThat(result.decision()).isEqualTo(CustomerCreditLimitGovernanceService.Decision.APPROVE);
        assertThat(result.projectedExposure()).isEqualByComparingTo("250000");
        assertThat(result.availableCredit()).isEqualByComparingTo("250000");
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void blocksSevereOverdueAndInsufficientLimit() {
        var result = service.assess(request("200000", 120, false, true, true));
        assertThat(result.decision()).isEqualTo(CustomerCreditLimitGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).anyMatch(item -> item.contains("90天"));
        assertThat(result.blockers()).anyMatch(item -> item.contains("暴露额"));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void reviewsIncreaseMissingFinancialEvidence() {
        var result = service.assess(request("600000", 0, false, false, false));
        assertThat(result.decision()).isEqualTo(CustomerCreditLimitGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(2);
    }

    private CustomerCreditLimitGovernanceService.Request request(String limit, int overdue,
                                                                  boolean sanctions,
                                                                  boolean statements,
                                                                  boolean evidence) {
        return new CustomerCreditLimitGovernanceService.Request("CR-100", "CUST-100",
                new BigDecimal("400000"), new BigDecimal(limit), new BigDecimal("180000"),
                new BigDecimal("70000"), overdue, CustomerCreditLimitGovernanceService.RiskGrade.MEDIUM,
                true, true, sanctions, false, statements, evidence);
    }
}
