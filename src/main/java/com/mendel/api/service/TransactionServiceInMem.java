package com.mendel.api.service;

import com.mendel.api.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TransactionServiceInMem implements TransactionService{

    private Map<Long, Transaction> transactionStore;

    public TransactionServiceInMem(){
        init();
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
       return transactionStore.values().stream()
                .filter(t -> t.getType()!=null && t.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public double getTransactionSum(long transactionId) {

        if(transactionStore.get(transactionId)==null)
            return -1;

        Transaction root = transactionStore.get(transactionId);

        double sum = root.getAmount();
        for(Transaction child : root.getChildren())
            sum += getTransactionSum(child.getId());

        return sum;
    }

    private void init(){
        transactionStore = new HashMap<>();

        AtomicLong counter = new AtomicLong();
        transactionStore.put(counter.get(), new Transaction(counter.getAndIncrement(), 150, "car"));
        transactionStore.put(counter.get(), new Transaction(counter.getAndIncrement(), 250, "car"));

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

        transactionStore.put(counter.getAndIncrement(), new Transaction(counter.getAndIncrement(), 450, "gym"));
    }
}
