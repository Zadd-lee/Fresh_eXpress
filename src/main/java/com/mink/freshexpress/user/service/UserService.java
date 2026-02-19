package com.mink.freshexpress.user.service;

import com.mink.freshexpress.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto findById(Long id);
}
