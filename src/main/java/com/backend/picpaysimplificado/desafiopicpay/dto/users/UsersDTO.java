package com.backend.picpaysimplificado.desafiopicpay.dto.users;

import java.math.BigDecimal;


import com.backend.picpaysimplificado.desafiopicpay.domain.users.enums.UserTypeEnum;

public record UsersDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserTypeEnum userType) {
    
}
