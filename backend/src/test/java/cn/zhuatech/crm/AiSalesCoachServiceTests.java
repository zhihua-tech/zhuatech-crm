/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm;

import cn.zhuatech.crm.ai.OpenAiCompatibleGateway;
import cn.zhuatech.crm.service.AiSalesCoachService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class AiSalesCoachServiceTests {
    private final AiSalesCoachService service = new AiSalesCoachService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));

    @Test void identifiesStalledOpportunity() {
        var result = service.coach(new AiSalesCoachService.Request("华东数字化项目", "方案", 45, 12, 1,
            true, List.of("预算偏高", "上线周期"), false));
        assertThat(result.riskLevel()).isEqualTo("HIGH");
        assertThat(result.nextActions()).hasSizeGreaterThan(2);
        assertThat(result.aiMode()).isEqualTo("LOCAL_RULES");
    }
}
