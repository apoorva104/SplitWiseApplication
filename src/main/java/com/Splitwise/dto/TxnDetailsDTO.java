package com.Splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxnDetailsDTO {
    private  long LenderId;
    private  long BrowwerId;
    private  double amount;

}
