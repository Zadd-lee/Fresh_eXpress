package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    //중복 요청
    EXIST_USER(HttpStatus.CONFLICT, "중복된 EMAIL을 가진 계정이 있습니다."),
    INVALID_ROLE_NAME(HttpStatus.BAD_REQUEST,"잘못된 권한 이름입니다" );



    private final HttpStatus httpStatus;
    private final String message;
}
