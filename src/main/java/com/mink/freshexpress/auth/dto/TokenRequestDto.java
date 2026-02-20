package com.mink.freshexpress.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;

}
