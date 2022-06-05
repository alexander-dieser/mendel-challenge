package com.mendel.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    private long id;
    private double amount;
    private String type;

    private List<Transaction> children = new ArrayList<>();
    private Transaction parent;

    public Transaction(long id, double amount, String type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(long id, double amount, String type, Transaction parent) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parent = parent;
    }

}
