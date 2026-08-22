/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm;

import cn.zhuatech.crm.service.LeadScoringService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LeadScoringServiceTests {
    private final LeadScoringService service = new LeadScoringService();

    @Test void marksQualifiedActiveLeadAsHot() {
        var result = service.score(new LeadScoringService.Request("L-202608-01", 92, 88, 84, 2, true, true));
        assertThat(result.grade()).isEqualTo("HOT");
        assertThat(result.score()).isGreaterThanOrEqualTo(75);
        assertThat(result.recommendedActions()).anyMatch(action -> action.contains("二十四小时"));
    }

    @Test void penalizesInactiveUnqualifiedLead() {
        var result = service.score(new LeadScoringService.Request("L-202608-02", 55, 40, 32, 35, false, false));
        assertThat(result.grade()).isEqualTo("COLD");
        assertThat(result.inactivityPenalty()).isEqualTo(15);
    }
}
