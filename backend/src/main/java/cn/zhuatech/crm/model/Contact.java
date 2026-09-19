/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.model;

import jakarta.persistence.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "crm_contact")
public class Contact extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "customer_id") private Customer customer;
    @Column(nullable = false, length = 50) private String name;
    @Column(length = 60) private String title;
    @Column(length = 30) private String phone;
    @Column(length = 120) private String email;
    @Column(nullable = false) private boolean primaryContact;
    @Column(length = 500) private String notes;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected Contact() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Contact(Customer customer, String name, String title, String phone, String email, boolean primaryContact, String notes) { this.customer=customer; this.name=name; this.title=title; this.phone=phone; this.email=email; this.primaryContact=primaryContact; this.notes=notes; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Customer getCustomer(){return customer;} /**
                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                     */
public String getName(){return name;} /**
                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                           */
public String getTitle(){return title;} /**
                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                   */
public String getPhone(){return phone;} /**
                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                           */
public String getEmail(){return email;} /**
                                                                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                   */
public boolean isPrimaryContact(){return primaryContact;} /**
                                                                                                                                                                                                                                                                             * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                             */
public String getNotes(){return notes;}
}
