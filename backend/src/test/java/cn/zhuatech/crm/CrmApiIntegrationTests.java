/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc
class CrmApiIntegrationTests {
    @Autowired MockMvc mvc;

    @Test void salesUserCanLoginAndReadOwnCustomers() throws Exception {
        String body=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"demo\",\"password\":\"Demo@2026\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.user.role").value("SALES")).andReturn().getResponse().getContentAsString();
        String token=JsonPath.read(body,"$.data.token");
        mvc.perform(get("/api/customers").header("Authorization","Bearer "+token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray()).andExpect(jsonPath("$.data[0].ownerName").value("知华销售"));
    }

    @Test void salesUserCanCreateCustomerAndRelatedFollowUp() throws Exception {
        String login=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"demo\",\"password\":\"Demo@2026\"}"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String token=JsonPath.read(login,"$.data.token");
        String customer=mvc.perform(post("/api/customers").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"集成测试客户\",\"level\":\"B\",\"status\":\"LEAD\",\"source\":\"自动化测试\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.name").value("集成测试客户")).andReturn().getResponse().getContentAsString();
        Number customerId=JsonPath.read(customer,"$.data.id");
        mvc.perform(post("/api/follow-ups").header("Authorization","Bearer "+token).contentType(MediaType.APPLICATION_JSON)
            .content("{\"customerId\":"+customerId.longValue()+",\"method\":\"PHONE\",\"content\":\"完成首次需求沟通\",\"nextAction\":\"发送产品资料\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.customerName").value("集成测试客户")).andExpect(jsonPath("$.data.method").value("PHONE"));
    }

    @Test void unauthenticatedRequestsAreRejected() throws Exception {
        mvc.perform(get("/api/dashboard")).andExpect(status().isForbidden());
    }

    @Test void salesUserCanEvaluateCustomerHealth() throws Exception {
        String login=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"demo\",\"password\":\"Demo@2026\"}"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String token=JsonPath.read(login,"$.data.token");
        mvc.perform(post("/api/customer-intelligence/health-score").header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerName\":\"华东示例客户\",\"engagementScore\":70,\"paymentRisk\":0.6,\"openOpportunities\":2,\"inactiveDays\":20,\"criticalComplaint\":true}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.healthScore").value(9))
            .andExpect(jsonPath("$.data.band").value("RISK"))
            .andExpect(jsonPath("$.data.managerReview").value(true));
    }

    @Test void salesUserCanGenerateWeightedOpportunityForecast() throws Exception {
        String login=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"demo\",\"password\":\"Demo@2026\"}"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String token=JsonPath.read(login,"$.data.token");
        mvc.perform(post("/api/customer-intelligence/opportunity-forecast").header("Authorization","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quarterTarget\":1000000,\"deals\":[{\"name\":\"华东数字化项目\",\"amount\":800000,\"probability\":75,\"stage\":\"NEGOTIATION\",\"expectedCloseDate\":\"2026-08-18\",\"daysSinceActivity\":3,\"criticalBlocker\":false},{\"name\":\"门店升级项目\",\"amount\":300000,\"probability\":40,\"stage\":\"PROPOSAL\",\"expectedCloseDate\":\"2026-08-12\",\"daysSinceActivity\":18,\"criticalBlocker\":true}]}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.weightedForecast").value(720000.0))
            .andExpect(jsonPath("$.data.commitForecast").value(600000.0))
            .andExpect(jsonPath("$.data.atRiskDeals").value(1))
            .andExpect(jsonPath("$.data.deals[0].name").value("门店升级项目"));
    }
}
