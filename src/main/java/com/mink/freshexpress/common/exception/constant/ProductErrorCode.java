package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode{
    UNIT_MISMATCH(HttpStatus.BAD_REQUEST, "잘못된 code 입니다."),
    STORAGE_TEMP_MISMATCH(HttpStatus.BAD_REQUEST,"잘못된 보관방법입니다." );
    private final HttpStatus httpStatus;
    private final String message;
}
