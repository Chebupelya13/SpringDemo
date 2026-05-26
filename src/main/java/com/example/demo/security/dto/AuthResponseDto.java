package com.example.demo.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    private final String jwtToken;
    private final String name;
    private final Long expiresAt;

    public AuthResponseDto(String jwtToken, String name, Long expiresAt) {
        this.jwtToken = jwtToken;
        this.name = name;
        this.expiresAt = expiresAt;
    }
}
