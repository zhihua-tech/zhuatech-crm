/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.model;

import jakarta.persistence.*;

@Entity @Table(name = "crm_contact")
public class Contact extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "customer_id") private Customer customer;
    @Column(nullable = false, length = 50) private String name;
    @Column(length = 60) private String title;
    @Column(length = 30) private String phone;
    @Column(length = 120) private String email;
    @Column(nullable = false) private boolean primaryContact;
    @Column(length = 500) private String notes;
    protected Contact() {}
    public Contact(Customer customer, String name, String title, String phone, String email, boolean primaryContact, String notes) { this.customer=customer; this.name=name; this.title=title; this.phone=phone; this.email=email; this.primaryContact=primaryContact; this.notes=notes; }
    public Customer getCustomer(){return customer;} public String getName(){return name;} public String getTitle(){return title;} public String getPhone(){return phone;} public String getEmail(){return email;} public boolean isPrimaryContact(){return primaryContact;} public String getNotes(){return notes;}
}
