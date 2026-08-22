/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm;

import cn.zhuatech.crm.service.NextBestActionService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class NextBestActionServiceTests {
    private final NextBestActionService service = new NextBestActionService();

    @Test void prioritizesExpiringAccountWithOpenIssues() {
        var result = service.recommend(new NextBestActionService.Request("C-1001", new BigDecimal("280000"), 18, 35, 1, 3, 7));
        assertThat(result.priority()).isEqualTo("URGENT");
        assertThat(result.nextBestAction()).contains("续约");
        assertThat(result.reasons()).contains("合同即将到期");
    }

    @Test void keepsHealthyOpportunityOnCadence() {
        var result = service.recommend(new NextBestActionService.Request("C-1002", new BigDecimal("50000"), 2, 80, 3, 0, 90));
        assertThat(result.priority()).isEqualTo("NORMAL");
        assertThat(result.reasons()).containsExactly("当前商机推进指标正常");
    }
}
