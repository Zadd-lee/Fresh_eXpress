package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(HttpStatus.NOT_FOUND, "파라미터 값을 확인해주세요."),
    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다"),
    AUTHENTICATE_ERROR(HttpStatus.FORBIDDEN,"권한 정보가 없습니다" );


    private final HttpStatus httpStatus;
    private final String message;

}