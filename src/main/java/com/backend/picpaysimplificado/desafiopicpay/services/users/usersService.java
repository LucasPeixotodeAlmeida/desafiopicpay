package com.backend.picpaysimplificado.desafiopicpay.services.users;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;
import com.backend.picpaysimplificado.desafiopicpay.domain.users.enums.UserTypeEnum;
import com.backend.picpaysimplificado.desafiopicpay.repositories.users.UsersRepository;

@Service
public class usersService {
    @Autowired
    private UsersRepository usersRepository;

    public void validateTransaction(UsersEntity sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() != UserTypeEnum.MERCHANT){
            throw new Exception("Lojistas não estão autorizados a relizar transações!");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente!");
        }
    }

    public UsersEntity findUsersById(UUID id) throws Exception{
        return this.usersRepository.findUsersById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUsers(UsersEntity user){
        this.usersRepository.save(user);
    }
    
}
