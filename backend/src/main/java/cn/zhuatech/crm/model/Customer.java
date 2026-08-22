/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "crm_customer")
public class Customer extends BaseEntity {
    public enum Status { LEAD, FOLLOWING, CUSTOMER, INACTIVE }
    @Column(nullable = false, length = 120) private String name;
    @Column(length = 60) private String shortName;
    @Column(length = 60) private String industry;
    @Column(nullable = false, length = 10) private String level = "B";
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status = Status.LEAD;
    @Column(length = 40) private String source;
    @Column(length = 30) private String phone;
    @Column(length = 120) private String email;
    @Column(length = 240) private String address;
    @Column(length = 1000) private String notes;
    private LocalDate nextFollowUpDate;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "owner_id") private UserAccount owner;

    protected Customer() {}
    public Customer(String name, UserAccount owner) { this.name = name; this.owner = owner; }
    public void update(String name, String shortName, String industry, String level, Status status, String source, String phone, String email, String address, LocalDate nextFollowUpDate, String notes) {
        this.name=name; this.shortName=shortName; this.industry=industry; this.level=level; this.status=status; this.source=source; this.phone=phone; this.email=email; this.address=address; this.nextFollowUpDate=nextFollowUpDate; this.notes=notes;
    }
    public void setNextFollowUpDate(LocalDate nextFollowUpDate) { this.nextFollowUpDate = nextFollowUpDate; }
    public String getName(){return name;} public String getShortName(){return shortName;} public String getIndustry(){return industry;} public String getLevel(){return level;} public Status getStatus(){return status;} public String getSource(){return source;} public String getPhone(){return phone;} public String getEmail(){return email;} public String getAddress(){return address;} public String getNotes(){return notes;} public LocalDate getNextFollowUpDate(){return nextFollowUpDate;} public UserAccount getOwner(){return owner;}
}
