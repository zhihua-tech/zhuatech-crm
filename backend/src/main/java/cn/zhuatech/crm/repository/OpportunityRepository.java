/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.repository;
import cn.zhuatech.crm.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OpportunityRepository extends JpaRepository<Opportunity,Long> {
    List<Opportunity> findAllByOrderByUpdatedAtDesc();
    List<Opportunity> findByOwnerOrderByUpdatedAtDesc(UserAccount owner);
    List<Opportunity> findByCustomerOrderByUpdatedAtDesc(Customer customer);
}
