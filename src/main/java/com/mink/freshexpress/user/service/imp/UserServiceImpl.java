package com.mink.freshexpress.user.service.imp;

import com.mink.freshexpress.auth.constant.Role;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import com.mink.freshexpress.user.controller.UpdateUserRequestDto;
import com.mink.freshexpress.user.dto.UserResponseDto;
import com.mink.freshexpress.user.model.User;
import com.mink.freshexpress.user.repository.UserRepository;
import com.mink.freshexpress.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.cert.CertStoreException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        User user = userRepository.findByIdOrElseThrows(id);
        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public void deleteById(Long id, String email) {
        User user = userRepository.findByIdOrElseThrows(id);

        validateTokenAndId(email, user);
        user.delete();
    }

    @Transactional
    @Override
    public void update(Long id, String email, UpdateUserRequestDto dto) {
        User user = userRepository.findByIdOrElseThrows(id);

        //validate
        // tokenUser to id
        validateTokenAndId(email, user);
        //user password and dto
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new CustomException(UserErrorCode.FOBBIDEN);
        }
        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        user.update(dto);

    }

    private static void validateTokenAndId(String email, User user) {
        if (!user.getEmail().equals(email)) {
            throw new CustomException(UserErrorCode.FOBBIDEN);
        }
    }
}
