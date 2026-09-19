/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class QuotationApprovalGovernanceService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Assessment assess(Request r) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!r.customerVerified() || !r.opportunityQualified()) blockers.add("客户或商机尚未完成有效性核验");
        if (!r.catalogVersionValid()) blockers.add("报价使用的产品目录或价格版本无效");
        if (r.discountRate().compareTo(r.authorizedDiscountRate()) > 0) blockers.add("折扣超过当前人员授权上限");
        if (r.grossMarginRate().compareTo(r.minimumMarginRate()) < 0) blockers.add("预计毛利率低于业务底线");
        if (!r.currencyAndTaxConfirmed()) blockers.add("币种、税率或含税口径未确认");
        if (!r.customerCreditPassed()) blockers.add("客户信用检查未通过");
        if (r.nonStandardTerms() && !r.legalReviewed()) blockers.add("非标准商务条款必须经过法务复核");
        if (r.ownerId().equals(r.approverId())) blockers.add("报价负责人不得审批自己的越权报价");
        if (!r.auditEvidenceAttached()) actions.add("补充成本测算、审批依据及报价版本证据");
        if (!r.followUpScheduled()) actions.add("设置报价有效期内的跟进任务");
        RiskLevel risk = r.nonStandardTerms() || r.discountRate().compareTo(new BigDecimal("0.20")) > 0 ? RiskLevel.HIGH : RiskLevel.NORMAL;
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.ESCALATE : Decision.APPROVE;
        String route = risk == RiskLevel.HIGH ? "销售经理→财务BP→法务/销售总监" : "销售经理";
        return new Assessment(r.quotationNo(), decision, risk, route, List.copyOf(blockers), List.copyOf(actions));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String quotationNo, @NotBlank String ownerId, @NotBlank String approverId,
                          @NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal discountRate,
                          @NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal authorizedDiscountRate,
                          @NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal grossMarginRate,
                          @NotNull @DecimalMin("0.00") @DecimalMax("1.00") BigDecimal minimumMarginRate,
                          boolean customerVerified, boolean opportunityQualified, boolean catalogVersionValid,
                          boolean currencyAndTaxConfirmed, boolean customerCreditPassed, boolean nonStandardTerms,
                          boolean legalReviewed, boolean auditEvidenceAttached, boolean followUpScheduled) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Assessment(String quotationNo, Decision decision, RiskLevel riskLevel, String approvalRoute,
                             List<String> blockers, List<String> actions) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { APPROVE, ESCALATE, BLOCKED }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum RiskLevel { NORMAL, HIGH }
}
