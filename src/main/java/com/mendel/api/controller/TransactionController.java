package com.mendel.api.controller;

import com.mendel.api.controller.requestentities.TransactionRequestBody;
import com.mendel.api.entities.Transaction;
import com.mendel.api.service.TransactionServiceInMem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "transactions")
public class TransactionController {

    @Autowired
    TransactionServiceInMem transactionServiceInMem;

    /**
     * Returns all the transactions according to a given type
     * @param type transaction type
     * @return  List of transactions
     */
    @GetMapping("types/{type}")
    public List<Transaction> getTransactionsByType(@PathVariable String type){
        return transactionServiceInMem.getTransactionsByType(type);
    }

    /**
     * Returns the total sum amount of all the nested transactions related to a given transaction
     * @param transactionId id of the root transaction
     * @return total sum
     */
    @GetMapping("sum/{transaction_id}")
    public double getTransactionsSum(@PathVariable("transaction_id") long transactionId){
        return transactionServiceInMem.getTransactionSum(transactionId);
    }

    /**
     * Add a new transaction
     * @param transactionId id of the new transaction
     * @param body transaction data
     */
    @PutMapping("{transaction_id}")
    public void addTransaction(@PathVariable("transaction_id") long transactionId, @RequestBody TransactionRequestBody body){
        transactionServiceInMem.addTransaction(transactionId, body);
    }

}
