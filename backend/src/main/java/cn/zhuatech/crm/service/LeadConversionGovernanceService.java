/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** 在线索转客户前统一执行资格、重复客户、授权和数据归属检查。 */
@Service
public class LeadConversionGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.requiredFieldsComplete()) blockers.add("线索转化必填字段不完整");
        if (!request.contactVerified()) blockers.add("联系人邮箱或手机尚未核验");
        if (!request.contactConsentRecorded()) blockers.add("缺少联系人数据处理及营销授权记录");
        if (!request.ownerAssigned()) blockers.add("尚未分配客户负责人");
        if (request.qualificationScore() < request.minimumQualificationScore()) blockers.add("线索资格分低于转化阈值");
        if (request.disqualified()) blockers.add("线索已被标记为无效或不适配");
        if (request.exactAccountMatch() && !request.existingAccountLinked()) {
            blockers.add("已命中相同法律主体，必须关联现有客户而非新建客户");
        }
        if (request.exactAccountMatch()
                && (request.matchedAccountId() == null || request.matchedAccountId().isBlank())) {
            blockers.add("精确匹配客户缺少可关联的客户编号");
        }
        if (request.possibleDuplicateCount() > 0 && !request.duplicateReviewCompleted()) {
            actions.add("完成企业名称、域名、电话和统一社会信用代码重复复核");
        }
        if (!request.sourceEvidenceArchived()) actions.add("归档线索来源、授权和资格判断证据");
        if (!request.downstreamSyncReady()) actions.add("准备联系人、商机和营销偏好下游同步");
        Decision decision;
        if (!blockers.isEmpty()) decision = Decision.BLOCKED;
        else if (!actions.isEmpty()) decision = Decision.REVIEW;
        else if (request.exactAccountMatch()) decision = Decision.LINK_EXISTING;
        else decision = Decision.CONVERT;
        return new Assessment(request.leadId(), decision, request.qualificationScore(),
                request.exactAccountMatch() ? request.matchedAccountId() : null,
                List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String leadId, @NotBlank String companyName,
                          @Email @NotBlank String contactEmail,
                          @Min(0) @Max(100) int qualificationScore,
                          @Min(0) @Max(100) int minimumQualificationScore,
                          @Min(0) int possibleDuplicateCount, boolean requiredFieldsComplete,
                          boolean contactVerified, boolean contactConsentRecorded, boolean ownerAssigned,
                          boolean disqualified, boolean exactAccountMatch, String matchedAccountId,
                          boolean existingAccountLinked, boolean duplicateReviewCompleted,
                          boolean sourceEvidenceArchived, boolean downstreamSyncReady) {}

    public record Assessment(String leadId, Decision decision, int qualificationScore,
                             String matchedAccountId, List<String> blockers, List<String> actions) {}

    public enum Decision { CONVERT, LINK_EXISTING, REVIEW, BLOCKED }
}
