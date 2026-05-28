package com.example.demo.security.service;

import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.Role;
import com.example.demo.security.dto.AuthRequestDto;
import com.example.demo.security.dto.AuthResponseDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequest) {
        UserResponseDto user = userService.getUserByUsername(authRequest.getUsername());

        if (user == null)
            throw new BadCredentialsException("Неверный логин или пароль");

        var token = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        String jwtToken;

        if (Objects.equals(user.role.getRole(), Role.Roles.USER)) {
            jwtToken = jwtTokenService.generateToken(authentication, Role.Roles.USER.name());
        } else {
            jwtToken = jwtTokenService.generateToken(authentication, Role.Roles.ADMIN.name());
        }

        Long expiresAt = jwtTokenService.extractExpirationTime(jwtToken);

        return new AuthResponseDto(jwtToken, authentication.getName(), expiresAt);
    }

}
