package com.backend.picpaysimplificado.desafiopicpay.repositories.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, UUID>{
    Optional<UsersEntity> findUsersByDocument(String document);

    Optional<UsersEntity> findUsersById(UUID id);
}
