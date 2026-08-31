# 企业级商机阶段门禁

商机阶段变更不应只依赖销售人员手工选择。本模块联合校验客户联系授权、阶段证据、折扣权限、决策人、下一步计划和交付可行性。

调用 `POST /api/enterprise/crm/opportunity-stage-gate` 可获得 `ADVANCE / REVIEW / BLOCKED` 决策，以及阻断原因和补充动作。生产环境应把结果关联商机版本、授权矩阵、审批流和审计日志。
