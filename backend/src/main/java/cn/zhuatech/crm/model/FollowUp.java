/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.model;

import jakarta.persistence.*;
import java.time.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "crm_follow_up")
public class FollowUp extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Method { PHONE, WECHAT, VISIT, EMAIL, OTHER }
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "customer_id") private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "opportunity_id") private Opportunity opportunity;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "creator_id") private UserAccount creator;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Method method;
    @Column(nullable = false, length = 1000) private String content;
    @Column(nullable = false) private LocalDateTime followUpAt;
    @Column(length = 500) private String nextAction;
    private LocalDate nextFollowUpDate;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected FollowUp() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public FollowUp(Customer customer, Opportunity opportunity, UserAccount creator, Method method, String content, LocalDateTime followUpAt, String nextAction, LocalDate nextFollowUpDate) { this.customer=customer; this.opportunity=opportunity; this.creator=creator; this.method=method; this.content=content; this.followUpAt=followUpAt; this.nextAction=nextAction; this.nextFollowUpDate=nextFollowUpDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Customer getCustomer(){return customer;} /**
                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                     */
public Opportunity getOpportunity(){return opportunity;} /**
                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                              */
public UserAccount getCreator(){return creator;} /**
                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                               */
public Method getMethod(){return method;} /**
                                                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                         */
public String getContent(){return content;} /**
                                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                     */
public LocalDateTime getFollowUpAt(){return followUpAt;} /**
                                                                                                                                                                                                                                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                              */
public String getNextAction(){return nextAction;} /**
                                                                                                                                                                                                                                                                                                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                */
public LocalDate getNextFollowUpDate(){return nextFollowUpDate;}
}
