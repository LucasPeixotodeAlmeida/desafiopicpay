package com.backend.picpaysimplificado.desafiopicpay.domain.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transactions")
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UsersEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UsersEntity receiver;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
