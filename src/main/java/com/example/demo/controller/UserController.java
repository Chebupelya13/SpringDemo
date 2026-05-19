package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.service.UsersDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(description = "Операции с пользователями", name = "Пользователи")
public class UserController {

    private final UsersDB usersDB;

    @Autowired
    public UserController(UsersDB usersDB) {
        this.usersDB = usersDB;
    }

    @GetMapping
    @Operation(description = "Получение списка всех пользователей")
    public List<User> getAllUsers() {
        return usersDB.getAllUsers();
    }

    @GetMapping("/{firstName}")
    @Operation(description = "Поиск пользователя по имени")
    public ArrayList<User> getUserByName(
            @RequestParam
            String firstName,
            @RequestParam
            String surName
    ) {
        ArrayList<User> user = usersDB.getUsersByName(firstName, surName);

        return user;
    }

    @GetMapping("/{phone}")
    @Operation(description = "Поиск пользователя по номеру телефона")
    public ResponseEntity<User> getUserByPhone(
            @PathVariable
            String phone
    ) {
        User user = usersDB.getUsersByPhone(phone);

        if ( user != null )
            return ResponseEntity.ok(user);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{passport}")
    @Operation(description = "Поиск пользователя по серии и номеру паспорта")
    public ResponseEntity<User> getUserByPassport(
            @PathVariable
            String passport
    ) {
        User user = usersDB.getUserByPassport(passport);

        if (user != null){
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(description = "Создание записи о новом пользователе")
    @PostMapping
    public HttpStatus createUser(
            @RequestBody
            User user
    ) {
        usersDB.addUser(user);

        return HttpStatus.CREATED;
    }

}
