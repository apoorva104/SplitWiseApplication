package com.Splitwise.repo;

import com.Splitwise.entity.GroupExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupExpenseRepo extends JpaRepository<GroupExpense,Long> {
    List<GroupExpenseRepo> findByGroupId(Long id);
}

