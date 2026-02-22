package com.mink.freshexpress.auth.service;

import com.mink.freshexpress.auth.controller.SigninRequestDto;
import com.mink.freshexpress.auth.dto.JwtAuthResponseDto;
import com.mink.freshexpress.auth.dto.LoginRequestDto;
import com.mink.freshexpress.auth.dto.TokenRequestDto;
import jakarta.validation.Valid;

public interface UserService {
    void signIn(@Valid SigninRequestDto dto);

    JwtAuthResponseDto login(@Valid LoginRequestDto dto);

    JwtAuthResponseDto refreshToken(TokenRequestDto tokenRequestDto);

    void logout(String accessToken);
}
