/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpportunityStageGateService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.contactConsentValid()) blockers.add("客户联系授权无效或已撤回");
        if (!request.stageEvidenceComplete()) blockers.add("目标阶段所需证据不完整");
        if (request.discountBps() > request.authorizedDiscountBps()) blockers.add("折扣超过当前人员授权额度");
        if (!request.decisionMakerIdentified()) actions.add("确认客户决策人和采购流程");
        if (!request.nextActionScheduled()) actions.add("登记下一步动作、负责人和截止时间");
        if (!request.closeDateFeasible()) actions.add("复核预计成交日期与交付能力");

        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.ADVANCE;
        return new Assessment(request.opportunityId(), request.targetStage(), decision,
                List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(
            @NotBlank String opportunityId,
            @NotBlank String targetStage,
            @Min(0) long amountCents,
            @Min(0) @Max(10000) int discountBps,
            @Min(0) @Max(10000) int authorizedDiscountBps,
            boolean contactConsentValid,
            boolean decisionMakerIdentified,
            boolean nextActionScheduled,
            boolean stageEvidenceComplete,
            boolean closeDateFeasible) {}

    public record Assessment(String opportunityId, String targetStage, Decision decision,
                             List<String> blockers, List<String> actions) {}
    public enum Decision { ADVANCE, REVIEW, BLOCKED }
}
