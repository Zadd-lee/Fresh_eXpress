package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 물품입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
