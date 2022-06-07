package com.mendel.api.service;

import com.mendel.api.controller.requestentities.TransactionRequestBody;
import com.mendel.api.entities.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceInMemTest {

    @Test
    void getTransactionsByTypeTestSmokeTest(){
        TransactionServiceInMem transactionService = new TransactionServiceInMem();

        Map<Long, Transaction> transactionStore = new HashMap<>();
        Transaction testTransaction = new Transaction(1, 150, "car", null);
        transactionStore.put(1L, testTransaction);
        transactionStore.put(2L, new Transaction(2, 150, "house", null));

        ReflectionTestUtils.setField(transactionService, "transactionStore", transactionStore);

        assertEquals(transactionService.getTransactionsByType("car").get(0), testTransaction);
    }

    @Test
    void getTransactionSumSmokeTest(){
        TransactionServiceInMem transactionService = new TransactionServiceInMem();
        ReflectionTestUtils.setField(transactionService, "transactionStore", initTransactionStoreGetTransactionSumSmokeTest());

        assertEquals(495, transactionService.getTransactionSum(999));
    }

    @Test
    void addTransactionSmokeTest(){
        TransactionServiceInMem transactionService = new TransactionServiceInMem();
        transactionService.addTransaction(1, new TransactionRequestBody("1","car","130", null));

        assertEquals(1, transactionService.getTransactionsByType("car").size());
    }

    private Map<Long, Transaction> initTransactionStoreGetTransactionSumSmokeTest(){
        Map<Long, Transaction> transactionStore = new HashMap<>();

        AtomicLong counter = new AtomicLong();

        Transaction parent = new Transaction(999, 350, "house");
        Transaction child1 = new Transaction(counter.getAndIncrement(), 50, "power supply", parent);
        Transaction child11 = new Transaction(counter.getAndIncrement(), 70, "power supply 2", child1);
        Transaction child2 = new Transaction(counter.getAndIncrement(), 25, "internet", parent);
        child1.setChildren(List.of(child11));
        parent.setChildren(List.of(child1, child2));

        transactionStore.put(parent.getId(), parent);
        transactionStore.put(child1.getId(), child1);
        transactionStore.put(child11.getId(), child11);
        transactionStore.put(child2.getId(), child2);

        return transactionStore;
    }
}
