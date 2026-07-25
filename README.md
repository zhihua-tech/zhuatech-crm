# ZhuaTech CRM — 知华科技 CRM 社区源码版

[![License](https://img.shields.io/badge/license-Community_Source_Noncommercial-orange.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg)](backend/pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F.svg)](backend/pom.xml)
[![Vue](https://img.shields.io/badge/Vue-3-42b883.svg)](frontend/package.json)

ZhuaTech CRM（知华 CRM）是由 **[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)** 提供源码的移动客户关系管理系统。项目采用 Java、Spring Boot、Vue 3、H5 和 MySQL 构建，包含客户、联系人、销售商机、跟进记录和销售任务等 CRM 基础能力，可用于个人学习 Java CRM、Vue CRM、销售管理系统和客户管理系统的设计与实现。

> [!IMPORTANT]
> **使用限制：本工程仅允许个人用于非商业性的学习、研究与技术交流，不得用于任何商业用途。企业内部使用、生产部署、SaaS、项目交付、咨询实施、二次开发后销售或其他直接、间接商业使用，均须事先取得上海如静知华信息科技有限公司的书面商业授权。完整条款请阅读 [LICENSE](LICENSE)。**

> 本项目为“源码开放/社区源码版”，因包含非商业限制，不属于 OSI 定义的开源软件。

> 官方网站：[https://www.zhuatech.cn/](https://www.zhuatech.cn/) · 商业授权、深度开发、私有化部署与定制功能，请联系知华科技。

## 功能特性

- 客户管理：客户档案、等级、状态、来源、行业、负责人和下次跟进日期
- 联系人管理：客户联系人、主要联系人、职位、电话、邮箱和备注
- 销售商机：预计金额、销售阶段、成交概率、预计成交日期和下一步计划
- 跟进记录：电话、微信、拜访、邮件等方式，跟进内容与后续行动可追溯
- 销售任务：关联客户、优先级、截止日期、完成状态和个人任务清单
- 销售工作台：客户数量、进行中商机、预计销售漏斗、待跟进和待办统计
- 权限基础：管理员、销售经理、销售人员角色及销售数据范围控制
- 移动 H5：面向手机端的客户卡片、商机推进、快速拨号与跟进录入
- 工程能力：JWT、MySQL 迁移、Docker Compose、Nginx 和 GitHub Actions CI

## 技术架构

| 层级 | 技术 |
| --- | --- |
| H5 前端 | Vue 3、Vite、Vant、Pinia、Vue Router、Axios |
| Java 后端 | Java 21、Spring Boot、Spring Security、Spring Data JPA、Flyway |
| 数据库 | MySQL 8.4（测试环境可使用 H2） |
| 部署 | Docker、Docker Compose、Nginx |

后端使用 `cn.zhuatech.crm` 根包名，前后端通过 REST API 解耦。详细设计见 [架构文档](docs/ARCHITECTURE.md) 和 [API 文档](docs/API.md)。

## 5 分钟启动

前置条件：Docker Desktop / Docker Engine 24+ 与 Docker Compose v2。以下方式仅供个人非商业学习环境使用；商业或生产部署前须取得书面授权。

```bash
cp .env.example .env
docker compose up --build -d
```

浏览器访问：<http://localhost:8088>

| 类型 | 账号 | 密码 |
| --- | --- | --- |
| 销售体验 | `demo` | `Demo@2026` |
| 销售经理 | `manager` | `Demo@2026` |
| 管理员 | `admin` | `ZhuaTech@2026` |

系统首次启动会创建两家示例客户、一条销售商机、联系人、跟进记录和销售任务，方便体验完整 CRM 流程。

> 体验密码与示例数据仅用于个人本地学习。部署到公网前必须修改初始化账号、数据库密码和 `JWT_SECRET`，并清除示例数据。

停止服务：

```bash
docker compose down
```

删除数据库卷会永久清除数据，仅在明确需要重置演示数据时执行：`docker compose down -v`。

## 本地开发

后端需要 JDK 21、Maven 3.9 和 MySQL 8：

```bash
cd backend
mvn spring-boot:run
```

前端需要 Node.js 24 与 npm 11：

```bash
cd frontend
npm install
npm run dev
```

默认开发地址为 <http://localhost:5173>，Vite 会将 `/api` 代理到 <http://localhost:8080>。环境变量说明见 [.env.example](.env.example)。

## 项目结构

```text
zhuatech-crm/
├── backend/        # cn.zhuatech.crm Java 后端
├── frontend/       # Vue 3 移动端 H5
├── deploy/         # 部署说明
├── docs/           # 架构与 REST API 文档
├── compose.yaml    # MySQL、后端与前端编排
└── README.md
```

## 路线图

- [ ] 线索池、公海客户、客户查重与分配回收
- [ ] 产品、报价、合同、订单、回款和开票管理
- [ ] 销售目标、业绩排行、漏斗分析和预测报表
- [ ] PC 管理后台、字段配置、操作审计和细粒度数据权限
- [ ] 企业微信、钉钉、短信、邮件和呼叫中心集成
- [ ] 多租户、开放 API、Webhook 与低代码流程配置

欢迎按 [贡献指南](CONTRIBUTING.md) 提交 Issue 和 Pull Request。安全问题请不要公开披露，处理方式见 [安全策略](SECURITY.md)。

## 使用许可与商业授权

本项目版权归 **上海如静知华信息科技有限公司** 所有，并按照 [ZhuaTech CRM 社区源码许可协议](LICENSE)提供源码：

- 允许自然人用于个人、非商业性的学习、研究、实验和技术交流。
- 允许为上述目的在个人设备上运行和修改，但必须保留许可证、版权与 NOTICE 声明。
- **未经我方事先书面授权，不得用于任何商业用途。** 企业内部使用、生产环境部署、SaaS、托管、项目交付、商业集成、收费或免费商业产品、咨询实施以及可产生直接或间接商业利益的使用，均属于商业使用。
- 商业使用、私有化部署或基于本工程进行商业二次开发，须联系知华科技取得书面商业授权。
- “知华科技”“ZhuaTech”相关名称及标识不因源码可见而授予商标许可。

如果你需要商业授权、CRM 系统深度开发、销售流程定制、私有化部署、系统集成或技术支持，请访问 **[知华科技官网](https://www.zhuatech.cn/)** 联系上海如静知华信息科技有限公司。

### 微信咨询

扫描下方任一二维码添加微信，可咨询 ZhuaTech CRM 部署、二次开发、功能定制及企业数字化解决方案。

<p align="center">
  <img src="docs/images/zhuatech-wechat-consulting.png" width="280" alt="知华科技微信咨询二维码一｜上海如静知华信息科技有限公司" />
  &nbsp;&nbsp;
  <img src="docs/images/zhuatech-wechat-consulting-2.png" width="280" alt="知华科技微信咨询二维码二｜上海如静知华信息科技有限公司" />
</p>

<p align="center">任选一个二维码扫码添加微信，联系知华科技</p>

### 相关关键词

CRM 社区源码、Java CRM 学习项目、Spring Boot CRM、Vue CRM、H5 客户管理、销售管理系统、客户关系管理系统、商机管理、客户跟进系统、CRM 商业授权、CRM 私有化部署。

---

Copyright © 2026 上海如静知华信息科技有限公司（知华科技）
