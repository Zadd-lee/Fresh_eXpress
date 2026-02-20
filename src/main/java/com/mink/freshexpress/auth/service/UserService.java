package com.mink.freshexpress.auth.service;

import com.mink.freshexpress.auth.controller.SigninRequestDto;
import com.mink.freshexpress.auth.dto.JwtAuthResponseDto;
import com.mink.freshexpress.auth.dto.LoginRequestDto;
import jakarta.validation.Valid;

public interface UserService {
    void signIn(@Valid SigninRequestDto dto);

    JwtAuthResponseDto login(@Valid LoginRequestDto dto);
}
