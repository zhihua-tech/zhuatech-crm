/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户信用额度调整前综合校验暴露额、逾期、风险、授信证据和审批路由。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class CustomerCreditLimitGovernanceService {
    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        BigDecimal projectedExposure = request.openReceivables().add(request.unbilledOrders());
        BigDecimal availableAfterChange = request.requestedLimit().subtract(projectedExposure);
        if (!request.customerActive()) blockers.add("客户已停用或冻结，不得调整授信");
        if (request.requestedLimit().compareTo(request.currentLimit()) > 0 && !request.requesterAuthorized()) {
            blockers.add("申请人无权提高客户信用额度");
        }
        if (request.sanctionsHit()) blockers.add("客户命中制裁或交易禁止名单");
        if (request.overdueDays() > 90) blockers.add("客户存在超过90天的逾期欠款");
        if (request.riskGrade() == RiskGrade.HIGH && !request.creditCommitteeApproved()) {
            blockers.add("高风险客户缺少信用委员会批准");
        }
        if (availableAfterChange.signum() < 0) blockers.add("申请额度低于当前应收与未开票订单暴露额");
        if (!request.financialStatementsCurrent()) actions.add("更新最近12个月财务报表与税务证明");
        if (request.requestedLimit().compareTo(request.currentLimit().multiply(new BigDecimal("1.5"))) > 0) {
            actions.add("额度增幅超过50%，补充保证金、担保或信用保险方案");
        }
        if (!request.reviewEvidenceAttached()) actions.add("归档授信调查、评级与审批证据");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.APPROVE;
        String route = request.riskGrade() == RiskGrade.HIGH || request.requestedLimit().compareTo(new BigDecimal("1000000")) >= 0
                ? "销售负责人→财务信控→信用委员会" : "销售负责人→财务信控";
        return new Assessment(request.requestNo(), request.customerId(), decision, projectedExposure,
                availableAfterChange, route, List.copyOf(blockers), List.copyOf(actions));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Request(@NotBlank String requestNo, @NotBlank String customerId,
                          @NotNull @DecimalMin("0.00") BigDecimal currentLimit,
                          @NotNull @DecimalMin("0.00") BigDecimal requestedLimit,
                          @NotNull @DecimalMin("0.00") BigDecimal openReceivables,
                          @NotNull @DecimalMin("0.00") BigDecimal unbilledOrders,
                          @Min(0) int overdueDays, @NotNull RiskGrade riskGrade,
                          boolean customerActive, boolean requesterAuthorized,
                          boolean sanctionsHit, boolean creditCommitteeApproved,
                          boolean financialStatementsCurrent, boolean reviewEvidenceAttached) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Assessment(String requestNo, String customerId, Decision decision,
                             BigDecimal projectedExposure, BigDecimal availableCredit,
                             String approvalRoute, List<String> blockers, List<String> actions) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum RiskGrade { LOW, MEDIUM, HIGH }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Decision { APPROVE, REVIEW, BLOCKED }
}
