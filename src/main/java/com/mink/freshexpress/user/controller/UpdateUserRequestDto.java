package com.mink.freshexpress.user.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UpdateUserRequestDto implements Serializable {
    @NotBlank
    private final String oldPassword;
    @NotBlank
    private final String newPassword;
    @NotBlank
    private final String name;
    @NotBlank
    private final String phone;
    @NotBlank
    private final String role;

}