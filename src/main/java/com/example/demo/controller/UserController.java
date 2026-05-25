package com.example.demo.controller;


import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Получение списка всех пользователей")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @PostMapping("/applications")
    @Operation(summary = "Получение всех заявок пользователя")
    public List<Application> getUsersApplications(
            @RequestParam int userId
    ){
        return userService.getUsersApplications(userId);
    }

    @PostMapping("/findByFilters")
    @Operation(summary = "Получение пользователя по фильтрам")
    public ResponseEntity<List<UserResponseDto>> getUserByFilters(
            @RequestBody UserRequestDto user
    ) {
        List<UserResponseDto> findUser = userService.getUserByFilters(user);

        return findUser == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(findUser);
    }

    @GetMapping("/findByFullName")
    @Operation(summary = "Поиск пользователя по имени")
    public ResponseEntity<List<User>> getUserByName(
            @RequestParam String firstName,
            @RequestParam String surName
    ) {
        List<User> users = userService.getUsersByName(firstName, surName);

        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/findByPhone")
    @Operation(summary = "Поиск пользователя по номеру телефона")
    public ResponseEntity<User> getUserByPhone(
            @RequestParam String phone
    ) {
        User user = userService.getUserByPhone(phone);

        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @GetMapping("/findByPassport")
    @Operation(summary = "Поиск пользователя по серии и номеру паспорта")
    public ResponseEntity<User> getUserByPassport(
            @RequestParam int passportSeries,
            @RequestParam int passportNumber
    ) {
        User user = userService.getUserByFullPassport(passportSeries, passportNumber);

        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @Operation(summary = "Создание записи о новом пользователе")
    @PostMapping
    public HttpStatus createUser(
            @RequestBody User user
    ) {
        userService.addUser(user);

        return HttpStatus.CREATED;
    }

}
