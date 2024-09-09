package com.Splitwise.entity;

import com.Splitwise.enums.TransEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "splitwise_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int transId;
    private double amount;
    private String members;

    @Enumerated(EnumType.STRING)
    private TransEnum status;
}
