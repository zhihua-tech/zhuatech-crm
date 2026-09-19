/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class LeadConversionGovernanceServiceTest {
    private final LeadConversionGovernanceService service = new LeadConversionGovernanceService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void convertsQualifiedUniqueLead() {
        var result = service.assess(request(false, false, true, true, 85));
        assertThat(result.decision()).isEqualTo(LeadConversionGovernanceService.Decision.CONVERT);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void linksVerifiedExactAccountMatch() {
        var result = service.assess(request(true, true, true, true, 85));
        assertThat(result.decision()).isEqualTo(LeadConversionGovernanceService.Decision.LINK_EXISTING);
        assertThat(result.matchedAccountId()).isEqualTo("ACC-9");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void reviewsPossibleDuplicateAndMissingClosureActions() {
        var input = new LeadConversionGovernanceService.Request("L-1", "知华客户", "buyer@example.com",
                85, 60, 2, true, true, true, true, false, false, null,
                false, false, false, false);
        var result = service.assess(input);
        assertThat(result.decision()).isEqualTo(LeadConversionGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(3);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksUnqualifiedLeadAndUnsafeNewAccount() {
        var result = service.assess(request(true, false, true, true, 40));
        assertThat(result.decision()).isEqualTo(LeadConversionGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).contains("线索资格分低于转化阈值", "已命中相同法律主体，必须关联现有客户而非新建客户");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private LeadConversionGovernanceService.Request request(boolean exactMatch, boolean linked,
                                                              boolean evidence, boolean sync, int score) {
        return new LeadConversionGovernanceService.Request("L-1", "知华客户", "buyer@example.com",
                score, 60, 0, true, true, true, true, false, exactMatch,
                exactMatch ? "ACC-9" : null, linked, true, evidence, sync);
    }
}
