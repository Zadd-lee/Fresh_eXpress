package com.mink.freshexpress.user.service.imp;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import com.mink.freshexpress.user.dto.UserResponseDto;
import com.mink.freshexpress.user.model.User;
import com.mink.freshexpress.user.repository.UserRepository;
import com.mink.freshexpress.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserResponseDto findById(Long id) {
        User user = userRepository.findByIdOrElseThrows(id);
        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public void deleteById(Long id, String email) {
        User user = userRepository.findByIdOrElseThrows(id);

        if (!user.getEmail().equals(email)) {
            throw new CustomException(UserErrorCode.FOBBIDEN);
        }
        user.delete();
    }
}
