package com.backend.picpaysimplificado.desafiopicpay.repositories.transaction;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.picpaysimplificado.desafiopicpay.domain.transaction.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID>{
    
}
