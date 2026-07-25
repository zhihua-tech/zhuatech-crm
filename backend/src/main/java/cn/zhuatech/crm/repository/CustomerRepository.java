/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.repository;
import cn.zhuatech.crm.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findAllByOrderByUpdatedAtDesc();
    List<Customer> findByOwnerOrderByUpdatedAtDesc(UserAccount owner);
    long countByOwner(UserAccount owner);
    long countByNextFollowUpDateLessThanEqual(LocalDate date);
    long countByOwnerAndNextFollowUpDateLessThanEqual(UserAccount owner, LocalDate date);
}
