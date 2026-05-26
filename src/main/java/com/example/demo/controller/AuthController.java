package com.example.demo.controller;

import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.security.dto.AuthRequestDto;
import com.example.demo.security.dto.AuthResponseDto;
import com.example.demo.security.service.AuthService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Schema
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        UserResponseDto user = userService.getUserByUsername(authRequest.getUsername());


        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}
