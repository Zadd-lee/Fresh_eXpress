package com.mink.freshexpress.user.service;

import com.mink.freshexpress.user.controller.UpdateUserRequestDto;
import com.mink.freshexpress.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto findById(Long id);

    void deleteById(Long id, String email);

    void update(Long id, String email, UpdateUserRequestDto dto);
}
