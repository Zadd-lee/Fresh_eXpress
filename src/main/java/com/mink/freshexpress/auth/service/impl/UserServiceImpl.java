package com.mink.freshexpress.auth.service.impl;

import com.mink.freshexpress.auth.controller.SigninRequestDto;
import com.mink.freshexpress.auth.model.User;
import com.mink.freshexpress.auth.repository.UserRepository;
import com.mink.freshexpress.auth.service.UserService;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public void signIn(SigninRequestDto dto) {
        //validate
        userRepository.findByEmail(dto.getEmail())
                        .ifPresent(user -> {
                            boolean isUnable = !user.isEnabled();
                            if (isUnable) {
                                throw new CustomException(UserErrorCode.CANCELED_USER);
                            }else {
                                throw new CustomException(UserErrorCode.CANCELED_USER);
                            }
                        });
        User user = dto.toEntity();
        user.updatePassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }
}
