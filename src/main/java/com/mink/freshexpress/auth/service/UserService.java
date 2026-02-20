package com.mink.freshexpress.auth.service;

import com.mink.freshexpress.auth.controller.SigninRequestDto;
import jakarta.validation.Valid;

public interface UserService {
    void signIn(@Valid SigninRequestDto dto);
}
