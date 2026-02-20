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
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
