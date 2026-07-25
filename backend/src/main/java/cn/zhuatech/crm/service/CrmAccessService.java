/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.service;

import cn.zhuatech.crm.common.BusinessException;
import cn.zhuatech.crm.model.*;
import cn.zhuatech.crm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CrmAccessService {
    private final CurrentUserService current;
    private final CustomerRepository customers;
    public CrmAccessService(CurrentUserService current, CustomerRepository customers){this.current=current;this.customers=customers;}
    public UserAccount current(){return current.get();}
    public boolean canViewAll(UserAccount user){return user.getRole()!=UserAccount.Role.SALES;}
    public Customer customer(Long id){Customer c=customers.findById(id).orElseThrow(()->new BusinessException("客户不存在"));assertCustomer(c,current());return c;}
    public void assertCustomer(Customer customer,UserAccount user){if(!canViewAll(user)&&!customer.getOwner().getId().equals(user.getId()))throw new BusinessException("无权访问该客户");}
}
