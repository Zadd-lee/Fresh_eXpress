package com.mink.freshexpress.auth.utils;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    @Value("${jwt.access-token-expiration}")
    @Getter
    private final long accessTokenExp;
    @Value("${jwt.refresh-token-expiration}")
    @Getter
    private final long refreshTokenExp;

    public JwtUtil(@Value("${jwt.secret}") final String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //Access Token 생성
    public String gene()
}
