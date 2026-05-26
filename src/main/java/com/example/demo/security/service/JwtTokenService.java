package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Autowired
    public JwtTokenService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    private String generateToken(Authentication authentication, String role) {
        Instant now = Instant.now();
        String scope = "ROLE_" + role.toUpperCase();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    public String generateUserToken(Authentication authentication) {
        return generateToken(authentication, Role.USER.name());
    }

    public String generateAdminToken(Authentication authentication) {
        return generateToken(authentication, Role.ADMIN.name());
    }

    public Long extractExpirationTime(String token) {
        Jwt jwt = decoder.decode(token);
        var exp = (Instant) jwt.getClaim("exp");
        return exp.toEpochMilli();
    }

    public String extractUsername(String token) {
        Jwt jwt = decoder.decode(token);
        return jwt.getClaim("sub");
    }

    public enum Role {
        USER,
        ADMIN
    }
}
