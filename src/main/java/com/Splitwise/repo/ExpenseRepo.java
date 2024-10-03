package com.Splitwise.repo;

import com.Splitwise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense,Long> {
    List<Expense> findByGroupId(long Id);
}
