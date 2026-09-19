/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.service;

import cn.zhuatech.crm.common.BusinessException;
import cn.zhuatech.crm.model.*;
import cn.zhuatech.crm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class CrmAccessService {
    private final CurrentUserService current;
    private final CustomerRepository customers;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public CrmAccessService(CurrentUserService current, CustomerRepository customers){this.current=current;this.customers=customers;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public UserAccount current(){return current.get();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public boolean canViewAll(UserAccount user){return user.getRole()!=UserAccount.Role.SALES;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Customer customer(Long id){Customer c=customers.findById(id).orElseThrow(()->new BusinessException("客户不存在"));assertCustomer(c,current());return c;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void assertCustomer(Customer customer,UserAccount user){if(!canViewAll(user)&&!customer.getOwner().getId().equals(user.getId()))throw new BusinessException("无权访问该客户");}
}
