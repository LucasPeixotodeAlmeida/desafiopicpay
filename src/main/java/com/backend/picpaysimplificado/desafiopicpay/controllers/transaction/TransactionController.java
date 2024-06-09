package com.backend.picpaysimplificado.desafiopicpay.controllers.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.picpaysimplificado.desafiopicpay.domain.transaction.entity.TransactionEntity;
import com.backend.picpaysimplificado.desafiopicpay.dto.transaction.TransactionDTO;
import com.backend.picpaysimplificado.desafiopicpay.services.transaction.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionEntity> createTransaction(@RequestBody TransactionDTO transaction) throws Exception{
        TransactionEntity newTransaction = this.transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
}
