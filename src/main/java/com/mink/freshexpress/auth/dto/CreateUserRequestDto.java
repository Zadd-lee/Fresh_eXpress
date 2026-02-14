package com.mink.freshexpress.auth.dto;

import com.mink.freshexpress.auth.constant.Role;
import com.mink.freshexpress.auth.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mink.freshexpress.auth.model.User}
 */
@Value
public class CreateUserRequestDto implements Serializable {
    @Email
    @NotBlank
    String email;

    @NotBlank
    @NotNull
    String password;

    @NotBlank
    String name;

    String phone;

    @NotBlank
    String role;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.email)
                .name(this.name)
                .phone(this.phone)
                .role(Role.of(role))
                .build();
    }
}