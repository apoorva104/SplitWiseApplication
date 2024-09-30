package com.Splitwise.repo;

import com.Splitwise.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransRepo extends JpaRepository<Transaction,Long> {
}
