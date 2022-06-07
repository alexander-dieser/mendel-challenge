package com.mendel.api.controller;

import com.mendel.api.entities.Transaction;
import com.mendel.api.service.TransactionServiceInMem;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceInMem transactionServiceInMem;

    @Test
    void getTransactionsByTypeSmokeTest() throws Exception {

        List<Transaction> transactionStore = new ArrayList<>();
        transactionStore.add(new Transaction(0, 150, "car", null));

        Mockito.when(transactionServiceInMem.getTransactionsByType("car")).thenReturn(transactionStore);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/transactions/types/car")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 0,\"amount\": 150.0,\"type\": \"car\",\"parent\": null}]"))
                .andReturn();
    }

    @Test
    void getTransactionsSumSmokeTest() throws Exception {

        Mockito.when(transactionServiceInMem.getTransactionSum(999)).thenReturn(Double.valueOf(100));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/transactions/sum/999")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("100.0"))
                .andReturn();
    }

}
