package com.mendel.api.service;

import com.mendel.api.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TransactionServiceInMem implements TransactionService{

    private List<Transaction> transactionStore;

    public TransactionServiceInMem(){
        init();
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
       return transactionStore.stream()
                .filter(t -> t.getType()!=null && t.getType().equals(type))
                .collect(Collectors.toList());
    }

    private void init(){
        transactionStore = new ArrayList<>();

        AtomicLong counter = new AtomicLong();
        transactionStore.add(new Transaction(counter.getAndIncrement(), 150, "car", null));
        transactionStore.add(new Transaction(counter.getAndIncrement(), 250, "car", null));

        Transaction parent = new Transaction(counter.getAndIncrement(), 350, "house", null);
        transactionStore.add(parent);
        transactionStore.add(new Transaction(counter.getAndIncrement(), 50, "power supply", parent));
        transactionStore.add(new Transaction(counter.getAndIncrement(), 25, "internet", parent));

        transactionStore.add(new Transaction(counter.getAndIncrement(), 450, "gym", null));
    }
}
