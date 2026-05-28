package com.example.demo.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

    public String jwtToken;
    public String name;
    public Long expiresAt;

    public AuthResponseDto(String jwtToken, String name, Long expiresAt) {
        this.jwtToken = jwtToken;
        this.name = name;
        this.expiresAt = expiresAt;
    }

}
