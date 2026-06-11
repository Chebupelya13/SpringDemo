package com.example.demo.service.security;

import com.example.demo.dto.request.AuthRequestDto;
import com.example.demo.dto.response.AuthResponseDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.enums.Roles;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UserResponseDto getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByUsername(username);
    }

    public Object getCredentials() {
        return SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequest) {
        UserResponseDto user = userService.getUserByUsername(authRequest.getUsername());

        if (user == null)
            throw new BadCredentialsException("Неверный логин или пароль");

        var token = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);

        String jwtToken;

        if (user.getRoles().stream().anyMatch(role -> role.getRole() == Roles.ADMIN)) {
            jwtToken = jwtTokenService.generateToken(authentication, Roles.ADMIN.name());
        } else {
            jwtToken = jwtTokenService.generateToken(authentication, Roles.USER.name());
        }

        Long expiresAt = jwtTokenService.extractExpirationTime(jwtToken);

        return new AuthResponseDto(jwtToken, authentication.getName(), expiresAt);
    }

}
