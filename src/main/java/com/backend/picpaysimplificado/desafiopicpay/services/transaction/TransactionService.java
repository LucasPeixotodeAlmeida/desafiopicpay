package com.backend.picpaysimplificado.desafiopicpay.services.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.picpaysimplificado.desafiopicpay.domain.transaction.entity.TransactionEntity;
import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;
import com.backend.picpaysimplificado.desafiopicpay.domain.users.enums.UserTypeEnum;
import com.backend.picpaysimplificado.desafiopicpay.dto.transaction.TransactionDTO;
import com.backend.picpaysimplificado.desafiopicpay.repositories.transaction.TransactionRepository;
import com.backend.picpaysimplificado.desafiopicpay.services.notification.NotificationService;
import com.backend.picpaysimplificado.desafiopicpay.services.users.UsersService;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Transactional
public TransactionEntity createTransaction(TransactionDTO transaction) throws Exception {
    UsersEntity sender = this.usersService.findUsersById(transaction.senderId());
    UsersEntity receiver = this.usersService.findUsersById(transaction.receiverId());

    if (sender.getUserType() == UserTypeEnum.MERCHANT) {
        throw new Exception("Lojistas não estão autorizados a realizar transações!");
    }

    usersService.validateTransaction(sender, transaction.amount());

    boolean isAuthorized = this.authorizeTransaction(sender, transaction.amount());

    if (!isAuthorized) {
        throw new Exception("Transação não autorizada");
    }

    TransactionEntity newTransaction = new TransactionEntity();
    newTransaction.setAmount(transaction.amount());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setCreatedAt(LocalDateTime.now());

    sender.setBalance(sender.getBalance().subtract(transaction.amount()));
    receiver.setBalance(receiver.getBalance().add(transaction.amount()));

    this.transactionRepository.save(newTransaction);
    this.usersService.saveUsers(sender);
    this.usersService.saveUsers(receiver);

    try {
        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");
    } catch (Exception e) {
        throw new RuntimeException("Erro ao enviar notificações, a transação foi revertida.", e);
    }

    return newTransaction;
}

public boolean authorizeTransaction(UsersEntity sender, BigDecimal amount) {
    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
        Map<String, Object> responseBody = authorizationResponse.getBody();
        if (responseBody != null) {
            String status = (String) responseBody.get("status");
            Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
            boolean authorization = data != null && (boolean) data.get("authorization");

            if ("success".equalsIgnoreCase(status) && authorization) {
                return true;
            } else {
                System.out.println("Authorization failed: " + responseBody);
                return false;
            }
        } else {
            System.out.println("Response body is null");
            return false;
        }
    } else {
        System.out.println("Authorization request failed with status: " + authorizationResponse.getStatusCode());
        return false;
    }
}

}
