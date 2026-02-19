package com.mink.freshexpress.auth.service.impl;

import com.mink.freshexpress.auth.controller.SigninRequestDto;
import com.mink.freshexpress.auth.dto.JwtAuthResponseDto;
import com.mink.freshexpress.auth.dto.LoginRequestDto;
import com.mink.freshexpress.auth.dto.TokenRequestDto;
import com.mink.freshexpress.auth.model.RefreshToken;
import com.mink.freshexpress.auth.model.User;
import com.mink.freshexpress.auth.repository.RefreshTokenRepository;
import com.mink.freshexpress.auth.repository.UserRepository;
import com.mink.freshexpress.auth.service.UserService;
import com.mink.freshexpress.auth.utils.JwtProvider;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CommonErrorCode;
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public void signIn(SigninRequestDto dto) {
        //validate
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    boolean isUnable = !user.isEnabled();
                    if (isUnable) {
                        throw new CustomException(UserErrorCode.CANCELED_USER);
                    } else {
                        throw new CustomException(UserErrorCode.CANCELED_USER);
                    }
                });
        User user = dto.toEntity();
        user.updatePassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }
    @Override
    public JwtAuthResponseDto login(LoginRequestDto dto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtAuthResponseDto tokenDto = jwtProvider.generateToken(authentication);

        RefreshToken refreshToken = new RefreshToken(authentication.getName(), tokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        return tokenDto;

    }
    @Override
    public JwtAuthResponseDto refreshToken(TokenRequestDto tokenRequestDto) {
        //1. refresh Token 검증 redis에서 검증
        if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(CommonErrorCode.UNAUTHORIZED);
        }
        
        //2. Access Token 에서 member id 가져오기
        Authentication authentication = jwtProvider.getAuthentication(tokenRequestDto.getAccessToken());


        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(CommonErrorCode.AUTHENTICATE_ERROR);
        }

        // 5. 새로운 토큰 생성
        JwtAuthResponseDto jwtAuthResponseDto =  jwtProvider.generateToken(authentication);

        RefreshToken newRefreshToken = new RefreshToken(authentication.getName(), jwtAuthResponseDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);


        // 토큰 발급
        return jwtAuthResponseDto;
    }



}
