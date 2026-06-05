package com.example.demo.controller;

import com.example.demo.dto.request.AuthRequestDto;
import com.example.demo.dto.response.AuthResponseDto;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Schema
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout() {
        System.out.println(authService.getCredentials());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
