package com.school_mall.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expireMinutes:1440}")
    private long expireMinutes;

    public String createToken(Long userId, String phone) {
        Instant now = Instant.now();
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(expireMinutes, ChronoUnit.MINUTES)))
                .withClaim("uid", userId)
                .withClaim("phone", phone)
                .sign(alg);
    }

    public DecodedJWT verify(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.require(alg).build().verify(token);
    }
} 