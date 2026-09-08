/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
class QuotationApprovalGovernanceServiceTest {
    private final QuotationApprovalGovernanceService service = new QuotationApprovalGovernanceService();
    private QuotationApprovalGovernanceService.Request request(BigDecimal discount, boolean evidence, boolean followUp) {
        return new QuotationApprovalGovernanceService.Request("Q-100", "sales-1", "manager-1", discount,
            new BigDecimal("0.25"), new BigDecimal("0.30"), new BigDecimal("0.20"),
            true, true, true, true, true, false, true, evidence, followUp);
    }
    @Test void approvesProfitableAuthorizedQuotation() {
        var a = service.assess(request(new BigDecimal("0.10"), true, true));
        assertThat(a.decision()).isEqualTo(QuotationApprovalGovernanceService.Decision.APPROVE);
    }
    @Test void escalatesIncompleteEvidence() {
        var a = service.assess(request(new BigDecimal("0.10"), false, false));
        assertThat(a.decision()).isEqualTo(QuotationApprovalGovernanceService.Decision.ESCALATE);
        assertThat(a.actions()).hasSize(2);
    }
    @Test void blocksUnauthorizedDiscountAndLowMargin() {
        var r = new QuotationApprovalGovernanceService.Request("Q-200", "u1", "u1", new BigDecimal("0.40"),
            new BigDecimal("0.15"), new BigDecimal("0.05"), new BigDecimal("0.20"), true, true, true,
            true, true, true, false, true, true);
        var a = service.assess(r);
        assertThat(a.decision()).isEqualTo(QuotationApprovalGovernanceService.Decision.BLOCKED);
        assertThat(a.riskLevel()).isEqualTo(QuotationApprovalGovernanceService.RiskLevel.HIGH);
        assertThat(a.blockers()).hasSizeGreaterThanOrEqualTo(4);
    }
}
