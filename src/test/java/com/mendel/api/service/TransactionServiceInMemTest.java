package com.mendel.api.service;

import com.mendel.api.entities.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceInMemTest {

    @Test
    void getTransactionsByTypeTestSmokeTest(){
        TransactionServiceInMem transactionService = new TransactionServiceInMem();

        List<Transaction> transactionStore = new ArrayList<>();
        Transaction testTransaction = new Transaction(1, 150, "car", null);
        transactionStore.add(testTransaction);
        transactionStore.add(new Transaction(1, 150, "house", null));

        ReflectionTestUtils.setField(transactionService, "transactionStore", transactionStore);

        assertEquals(transactionService.getTransactionsByType("car").get(0), testTransaction);
    }
}
