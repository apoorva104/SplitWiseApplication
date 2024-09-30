package com.Splitwise.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "splitwise_user_expense")
public class UserExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long groupId;
    private Long lender;

    private double amt;
    private Long borrower;

}
