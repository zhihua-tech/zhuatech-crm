# 企业级客户归属转移治理

`POST /api/enterprise/crm/account-ownership-transfer` 用于销售人员离职、区域调整、客户重分配和大客户接管前的统一检查。

系统校验申请权限、目标账号状态、客户冻结状态、销售区域、客户容量、保护客户审批与渠道冲突，并要求在途商机、报价、合同和回款具备交接计划。返回 `TRANSFER / REVIEW / BLOCKED`、审批路径、阻断原因和待办动作，避免客户被静默转移、无人跟进或形成归属争议。

商业授权或定制开发请微信添加微信号 `zhuatech` 或 `zhuatech2` 进行咨询。官网：[知华科技](https://www.zhuatech.cn/)。
