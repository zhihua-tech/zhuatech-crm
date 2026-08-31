/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OpportunityStageGateServiceTest {
    private final OpportunityStageGateService service = new OpportunityStageGateService();

    @Test void advancesQualifiedOpportunity() {
        var result = service.assess(new OpportunityStageGateService.Request(
                "OPP-001", "PROPOSAL", 8_000_000, 500, 800, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(OpportunityStageGateService.Decision.ADVANCE);
        assertThat(result.blockers()).isEmpty();
    }

    @Test void blocksConsentAndDiscountViolations() {
        var result = service.assess(new OpportunityStageGateService.Request(
                "OPP-002", "NEGOTIATION", 20_000_000, 1800, 1000, false, false, false, false, false));
        assertThat(result.decision()).isEqualTo(OpportunityStageGateService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(3);
        assertThat(result.actions()).hasSize(3);
    }
}
