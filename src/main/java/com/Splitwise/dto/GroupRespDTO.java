package com.Splitwise.dto;

import com.Splitwise.entity.Expense;
import com.Splitwise.repo.ExpenseRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRespDTO {
    long expenseId;
    double amount;
    String expenseName;
    String addedBy;

}
