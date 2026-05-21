package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(description = "Операции с пользователями", name = "Пользователи")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserDao userDao) {
        this.userService = userDao;
    }

    @GetMapping
    @Operation(description = "Получение списка всех пользователей")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/findByFullName")
    @Operation(description = "Поиск пользователя по имени")
    public ResponseEntity<List<User>> getUserByName(
            @RequestParam
            String firstName,
            @RequestParam
            String surName
    ) {
        List<User> users = userService.getUsersByName(firstName, surName);

        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/findByPhone/{phone}")
    @Operation(description = "Поиск пользователя по номеру телефона")
    public ResponseEntity<User> getUserByPhone(
            @PathVariable
            String phone
    ) {
        User user = userService.getUsersByPhone(phone);

        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @GetMapping("/findByPassport/{passport}")
    @Operation(description = "Поиск пользователя по серии и номеру паспорта")
    public ResponseEntity<User> getUserByPassport(
            @PathVariable
            String passport
    ) {
        User user = userService.getUserByPassport(passport);

        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @Operation(description = "Создание записи о новом пользователе")
    @PostMapping
    public HttpStatus createUser(
            @RequestBody
            User user
    ) {
        userService.addUser(user);

        return HttpStatus.CREATED;
    }

}
