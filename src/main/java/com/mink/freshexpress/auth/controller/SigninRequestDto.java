package com.mink.freshexpress.auth.controller;

import com.mink.freshexpress.auth.constant.Role;
import com.mink.freshexpress.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequestDto {
    @Email
    @NotBlank
    String email;
    @NotBlank
    String password;
    @NotBlank
    String name;
    String phone;
    @NotNull
    String role;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .phone(this.phone)
                .role(Role.of(role))
                .build();
    }

}