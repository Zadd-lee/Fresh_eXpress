package com.mink.freshexpress.auth.utils;

import com.mink.freshexpress.auth.dto.AuthenticationScheme;
import com.mink.freshexpress.auth.dto.JwtAuthResponseDto;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CommonErrorCode;
import com.mink.freshexpress.common.model.RedisDao;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;
    private final long accessTokenExp;
    private final long refreshTokenExp;
    private final RedisDao redisDao;

    //todo refact 필요
    private static final long THREE_DAYS = 1000 * 60 * 60 * 24 * 3;  // 3일


    public JwtProvider(@Value("${jwt.secret}") final String key
    , @Value("${jwt.access-token-expiration}") Long accessTokenExp
    , @Value("${jwt.refresh-token-expiration}") Long refreshTokenExp
            , RedisDao redisDao) {
        this.redisDao = redisDao;
        byte[] keyBytes = Decoders.BASE64URL.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshTokenExp = refreshTokenExp;
        this.accessTokenExp = accessTokenExp;
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하기
    public JwtAuthResponseDto generateToken(Authentication authentication) {
        // 권한 가져오기
        // JWT 토큰의 claims에 포함되어 사용자의 권한 정보를 저장하는데 사용됨
        String authorities = authentication.getAuthorities().stream() // Authentication 객체에서 사용자 권한 목록 가져오기
                .map(GrantedAuthority::getAuthority) // 각 GrantedAuthority 객체에서 권한 문자열만 추출하기
                .collect(Collectors.joining(",")); // 추출된 권한 문자열들을 쉼표로 구분하여 하나의 문자열로 결합하기

        long now = (new Date()).getTime();
        String username = authentication.getName();

        // AccessToken 생성
        Date accessTokenExpire = new Date(now + accessTokenExp);
        String accessToken = generateAccessToken(username, authorities, accessTokenExpire);

        // RefreshToken 생성
        Date refreshTokenExpire = new Date(now + refreshTokenExp);
        String refreshToken = generateRefreshToken(username, refreshTokenExpire);

        return JwtAuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenAuthScheme(AuthenticationScheme.BEARER.getName())
                .build();
    }
    private String generateAccessToken(String username, String authorities, Date expireDate) {
        return Jwts.builder()
                .setSubject(username) // 토큰 제목 (사용자 이름)
                .claim("auth", authorities) // 권한 정보 (커스텀 클레임)
                .setExpiration(expireDate) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 지정된 키와 알고리즘으로 서명
                .compact(); // 최종 JWT 문자열 생성 (header.payload.signature 구조);
    }

    private String generateRefreshToken(String username, Date expireDate) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //JWT 토큰을 복호화해 토큰에 들어있는 정보를 꺼내는 메서드
    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new CustomException(CommonErrorCode.AUTHENTICATE_ERROR);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // RefreshToken 검증
    public boolean validateRefreshToken(String token) {
        // 기본적인 JWT 검증
        if (!validateToken(token)) return false;

        try {
            // token에서 username 추출하기
            String username = getUserNameFromToken(token);
            // Redis에 저장된 RefreshToken과 비교하기
            String redisToken = (String) redisDao.getValues(username);
            return token.equals(redisToken);
        } catch (Exception e) {
            log.info("RefreshToken Validation Failed", e);
            return false;
        }
    }
    // 토큰에서 username 추출
    public String getUserNameFromToken(String token) {
        try {

            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key)   // SecretKey 타입이어야 함
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            // 토큰이 만료되어도 클레임 내용을 가져올 수 있음
            return e.getClaims().getSubject();
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

