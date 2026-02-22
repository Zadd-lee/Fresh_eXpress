package com.mink.freshexpress.auth.repository;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import com.mink.freshexpress.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    default User findByIdOrElseThrows(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));
    }
}
