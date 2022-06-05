package com.mendel.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    private long id;
    private double amount;
    private String type;

    private Transaction parent;
}
