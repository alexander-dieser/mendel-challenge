package com.mendel.api.service;

import com.mendel.api.controller.requestentities.TransactionRequestBody;
import com.mendel.api.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TransactionServiceInMem implements TransactionService{
    Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<Long, Transaction> transactionStore;

    public TransactionServiceInMem() {
        this.transactionStore = new HashMap<>();
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

    @Override
    public void addTransaction(long transactionId, TransactionRequestBody body) {

        Transaction transaction = new Transaction(transactionId, Double.parseDouble(body.getAmount()), body.getType());

        if(body.getParentId()!=null) {
            Transaction parent = transactionStore.get(Long.parseLong(body.getParentId()));
            if(parent==null)
                logger.warning("Parent is null");
            else {
                transaction.setParent(parent);
                parent.getChildren().add(transaction);
            }
        }

        transactionStore.put(transactionId, transaction);
    }
}
