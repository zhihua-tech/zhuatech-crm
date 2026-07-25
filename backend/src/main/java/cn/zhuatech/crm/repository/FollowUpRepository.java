/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.repository;
import cn.zhuatech.crm.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FollowUpRepository extends JpaRepository<FollowUp,Long> {
    List<FollowUp> findByCustomerOrderByFollowUpAtDesc(Customer customer);
    List<FollowUp> findTop10ByCreatorOrderByFollowUpAtDesc(UserAccount creator);
}
