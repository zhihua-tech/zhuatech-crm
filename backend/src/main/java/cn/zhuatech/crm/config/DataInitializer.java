/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.config;

import cn.zhuatech.crm.model.*;
import cn.zhuatech.crm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository users; private final CustomerRepository customers; private final ContactRepository contacts; private final OpportunityRepository opportunities; private final FollowUpRepository followUps; private final SalesTaskRepository tasks; private final PasswordEncoder encoder;
    public DataInitializer(UserRepository users,CustomerRepository customers,ContactRepository contacts,OpportunityRepository opportunities,FollowUpRepository followUps,SalesTaskRepository tasks,PasswordEncoder encoder){this.users=users;this.customers=customers;this.contacts=contacts;this.opportunities=opportunities;this.followUps=followUps;this.tasks=tasks;this.encoder=encoder;}
    @Override @Transactional public void run(String... args){
        if(users.count()>0)return;
        UserAccount admin=new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN);admin.updateProfile("contact@zhuatech.cn","021-00000000","CRM 管理员");users.save(admin);
        UserAccount demo=new UserAccount("demo",encoder.encode("Demo@2026"),"知华销售",UserAccount.Role.SALES);demo.updateProfile("demo@zhuatech.cn","13800000000","客户经理");users.save(demo);
        UserAccount manager=new UserAccount("manager",encoder.encode("Demo@2026"),"销售经理",UserAccount.Role.SALES_MANAGER);manager.updateProfile("manager@zhuatech.cn","13900000000","销售总监");users.save(manager);
        Customer c1=new Customer("上海星河智能制造有限公司",demo);c1.update("上海星河智能制造有限公司","星河智造","智能制造","A",Customer.Status.FOLLOWING,"官网咨询","021-60000001","contact@xinghe.example","上海市浦东新区",LocalDate.now(),"关注 CRM 私有化部署与销售流程定制");customers.save(c1);
        Customer c2=new Customer("杭州云帆数字科技有限公司",demo);c2.update("杭州云帆数字科技有限公司","云帆数字","软件服务","B",Customer.Status.LEAD,"客户转介绍","0571-80000002","hello@yunfan.example","杭州市滨江区",LocalDate.now().plusDays(2),"已发送产品资料");customers.save(c2);
        contacts.save(new Contact(c1,"陈经理","信息化负责人","13811112222","chen@xinghe.example",true,"决策影响人"));
        contacts.save(new Contact(c2,"林女士","运营总监","13911113333","lin@yunfan.example",true,"关注移动端体验"));
        Opportunity op=opportunities.save(new Opportunity(c1,demo,"CRM 私有化建设项目",new BigDecimal("280000"),Opportunity.Stage.PROPOSAL,55,LocalDate.now().plusMonths(1),"确认接口与部署范围"));
        followUps.save(new FollowUp(c1,op,demo,FollowUp.Method.WECHAT,"已沟通客户管理、商机阶段和销售报表需求，客户希望安排产品演示。",LocalDateTime.now().minusDays(1),"准备演示环境并发送会议邀请",LocalDate.now()));
        tasks.save(new SalesTask(demo,c1,"准备星河智造产品演示","整理客户场景和演示数据",LocalDate.now().plusDays(1),"HIGH"));
        tasks.save(new SalesTask(demo,c2,"回访云帆数字","确认资料阅读情况和下一步计划",LocalDate.now().plusDays(2),"MEDIUM"));
    }
}
