package com.example.demo.controller;


import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.dto.response.UserResponseDto;
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
    public ResponseEntity<ListResponseDto<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/applications")
    @Operation(summary = "Получение всех заявок пользователя")
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getUsersApplications(
            @RequestParam int userId
    ){
        UserResponseDto user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ListResponseDto<>(user.getApplications()));
    }

    @PostMapping("/findByFilters")
    @Operation(summary = "Получение пользователя по фильтрам")
    public ResponseEntity<ListResponseDto<UserResponseDto>> getUserByFilters(
            @RequestBody UserRequestDto user
    ) {
        return ResponseEntity.ok(userService.getUserByFilters(user));
    }

    @GetMapping("/findByFullName")
    @Operation(summary = "Поиск пользователя по имени")
    public ResponseEntity<ListResponseDto<UserResponseDto>> getUserByName(
            @RequestParam String firstName,
            @RequestParam String surName
    ) {
        return ResponseEntity.ok(userService.getUsersByName(firstName, surName));
    }

    @GetMapping("/findByPhone")
    @Operation(summary = "Поиск пользователя по номеру телефона")
    public ResponseEntity<UserResponseDto> getUserByPhone(
            @RequestParam String phone
    ) {
        UserResponseDto user = userService.getUserByPhone(phone);

        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @GetMapping("/findByPassport")
    @Operation(summary = "Поиск пользователя по серии и номеру паспорта")
    public ResponseEntity<UserResponseDto> getUserByPassport(
            @RequestParam int passportSeries,
            @RequestParam int passportNumber
    ) {
        UserResponseDto user = userService.getUserByFullPassport(passportSeries, passportNumber);

        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @Operation(summary = "Создание записи о новом пользователе")
    @PostMapping
    public HttpStatus createUser(
            @RequestBody UserRequestDto user
    ) {
        userService.addUser(user);

        return HttpStatus.CREATED;
    }

    @Operation(summary = "Выдача админа (для тестирования)")
    @PutMapping("/admin")
    public HttpStatus giveRoot(
            @RequestParam int userId
    ) {
        userService.giveRoot(userId);

        return HttpStatus.OK;
    }

}
