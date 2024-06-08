package com.backend.picpaysimplificado.desafiopicpay.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;
import com.backend.picpaysimplificado.desafiopicpay.dto.users.UsersDTO;
import com.backend.picpaysimplificado.desafiopicpay.services.users.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersEntity> createUser(@RequestBody UsersDTO user){
        UsersEntity newUser = usersService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsersEntity>> getAllUsers(){
        List<UsersEntity> users = this.usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
