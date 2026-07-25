/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "crm_opportunity")
public class Opportunity extends BaseEntity {
    public enum Stage { LEAD, DISCOVERY, PROPOSAL, NEGOTIATION, WON, LOST }
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "customer_id") private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "owner_id") private UserAccount owner;
    @Column(nullable = false, length = 120) private String name;
    @Column(nullable = false, precision = 15, scale = 2) private BigDecimal amount = BigDecimal.ZERO;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Stage stage = Stage.LEAD;
    @Column(nullable = false) private Integer probability = 10;
    private LocalDate expectedCloseDate;
    @Column(length = 500) private String nextStep;
    protected Opportunity() {}
    public Opportunity(Customer customer, UserAccount owner, String name, BigDecimal amount, Stage stage, int probability, LocalDate expectedCloseDate, String nextStep) { this.customer=customer; this.owner=owner; this.name=name; this.amount=amount; this.stage=stage; this.probability=probability; this.expectedCloseDate=expectedCloseDate; this.nextStep=nextStep; }
    public void changeStage(Stage stage, int probability, String nextStep) { this.stage=stage; this.probability=probability; this.nextStep=nextStep; }
    public Customer getCustomer(){return customer;} public UserAccount getOwner(){return owner;} public String getName(){return name;} public BigDecimal getAmount(){return amount;} public Stage getStage(){return stage;} public Integer getProbability(){return probability;} public LocalDate getExpectedCloseDate(){return expectedCloseDate;} public String getNextStep(){return nextStep;}
}
