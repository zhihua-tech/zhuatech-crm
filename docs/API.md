# ZhuaTech CRM REST API

Copyright © 2026 上海如静知华信息科技有限公司。

基础路径为 `/api`。除登录外，请在请求头中传入 `Authorization: Bearer <token>`。统一响应结构：

```json
{"success":true,"message":"操作成功","data":{},"timestamp":"2026-07-25T00:00:00Z"}
```

| 方法 | 路径 | 功能 |
| --- | --- | --- |
| POST | `/auth/login` | 登录并获取 JWT |
| GET | `/auth/me` | 获取当前用户 |
| GET | `/dashboard` | 客户、商机、漏斗、跟进和任务统计 |
| GET / POST | `/customers` | 查询 / 新建客户 |
| GET / PUT | `/customers/{id}` | 客户详情 / 更新客户 |
| GET / POST | `/contacts?customerId={id}` | 客户联系人列表 / 新建联系人 |
| DELETE | `/contacts/{id}` | 删除联系人 |
| GET / POST | `/opportunities` | 商机列表 / 新建商机 |
| PATCH | `/opportunities/{id}/stage` | 推进商机阶段和成交概率 |
| GET | `/follow-ups?customerId={id}` | 客户跟进历史 |
| GET | `/follow-ups/recent` | 当前销售最近十条跟进 |
| POST | `/follow-ups` | 新建跟进记录并更新下次跟进日期 |
| GET / POST | `/tasks` | 销售任务列表 / 新建任务 |
| PATCH / DELETE | `/tasks/{id}` | 更新任务状态 / 删除本人任务 |

销售人员只能访问自己负责的客户及其关联数据；销售经理和管理员可查看全部客户与商机。时间使用 ISO 8601 格式，例如 `2026-07-25T09:00:00`。

## 关键枚举

- 客户状态：`LEAD`、`FOLLOWING`、`CUSTOMER`、`INACTIVE`
- 商机阶段：`LEAD`、`DISCOVERY`、`PROPOSAL`、`NEGOTIATION`、`WON`、`LOST`
- 跟进方式：`PHONE`、`WECHAT`、`VISIT`、`EMAIL`、`OTHER`
- 任务优先级：`LOW`、`MEDIUM`、`HIGH`

## 客户健康度

`POST /api/customer-intelligence/health-score`：依据互动、付款、商机、静默和客诉信息返回健康分层与经营动作。

## 下一最佳销售动作

`POST /api/crm/insights/next-best-action`：根据客户阶段、跟进间隔、关系覆盖、未结问题和合同期限返回优先级、推荐动作与可解释原因。
