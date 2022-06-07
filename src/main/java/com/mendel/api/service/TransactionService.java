package com.mendel.api.service;

import com.mendel.api.controller.requestentities.TransactionRequestBody;
import com.mendel.api.entities.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByType(String type);
    double getTransactionSum(long transactionId);
    void addTransaction(long transactionId, TransactionRequestBody body);
}
