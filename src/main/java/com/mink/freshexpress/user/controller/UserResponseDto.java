package com.mink.freshexpress.user.controller;

import com.mink.freshexpress.auth.constant.Role;
import com.mink.freshexpress.user.model.User;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mink.freshexpress.user.model.User}
 */
@Value
public class UserResponseDto implements Serializable {
    Long id;
    String email;
    String name;
    String phone;
    boolean enabled;
    String role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.enabled = user.isEnabled();
        this.role = user.getRole().getName();
    }
}