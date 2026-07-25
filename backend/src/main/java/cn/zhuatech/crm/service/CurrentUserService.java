/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.service;
import cn.zhuatech.crm.common.BusinessException;
import cn.zhuatech.crm.model.UserAccount;
import cn.zhuatech.crm.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class CurrentUserService {
    private final UserRepository users;
    public CurrentUserService(UserRepository users) { this.users = users; }
    public UserAccount get() { String name = SecurityContextHolder.getContext().getAuthentication().getName(); return users.findByUsername(name).orElseThrow(() -> new BusinessException("登录状态已失效")); }
}
