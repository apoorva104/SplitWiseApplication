package com.Splitwise.repo;

import com.Splitwise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupExpenseRepo extends JpaRepository<Expense,Long> {
    List<GroupExpenseRepo> findByGroupId(Long id);
}

