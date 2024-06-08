package com.backend.picpaysimplificado.desafiopicpay.dto.transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(BigDecimal amount, UUID senderId, UUID receiverId) {
    
}
