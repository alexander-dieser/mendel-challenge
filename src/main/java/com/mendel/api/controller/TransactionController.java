package com.mendel.api.controller;

import com.mendel.api.entities.Transaction;
import com.mendel.api.service.TransactionServiceInMem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "transactions")
public class TransactionController {

    @Autowired
    TransactionServiceInMem transactionServiceInMem;

    @GetMapping("types/{type}")
    public List<Transaction> getTransactionsByType(@PathVariable String type){
        return transactionServiceInMem.getTransactionsByType(type);
    }

}
