/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.crm.repository;
import cn.zhuatech.crm.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ContactRepository extends JpaRepository<Contact,Long> { List<Contact> findByCustomerOrderByPrimaryContactDescCreatedAtAsc(Customer customer); }
