package com.mendel.api.service;

import com.mendel.api.entities.Transaction;

import java.util.List;

public interface TransactionService {
    public List<Transaction> getTransactionsByType(String type);
    double getTransactionSum(long transaction_id);
}
