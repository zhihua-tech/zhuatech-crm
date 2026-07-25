/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.crm.repository;
import cn.zhuatech.crm.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface SalesTaskRepository extends JpaRepository<SalesTask,Long> {
    List<SalesTask> findByAssigneeOrderByCompletedAscDueDateAsc(UserAccount assignee);
    Optional<SalesTask> findByIdAndAssignee(Long id, UserAccount assignee);
    long countByAssigneeAndCompletedFalse(UserAccount assignee);
}
