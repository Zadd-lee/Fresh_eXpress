package com.mink.freshexpress.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JwtAuthResponseDto {
    //토근 인증 스키마
    private String tokenAuthScheme;
    //엑세스 토큰
    private String accessToken;
    //리프레시 토큰
    private String refreshToken;

}
