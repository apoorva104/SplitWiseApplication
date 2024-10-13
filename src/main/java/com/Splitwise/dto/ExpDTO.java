package com.Splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpDTO {
    long expenseId;
    double amount;
    String expenseName;
    String addedBy;
}
