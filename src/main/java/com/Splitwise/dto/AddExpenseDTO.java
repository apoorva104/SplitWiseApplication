package com.Splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExpenseDTO {
    private String expName;
    private Long groupId;
    private  Long addedBy;
    private List<Long> members;
    private double amount;

}
