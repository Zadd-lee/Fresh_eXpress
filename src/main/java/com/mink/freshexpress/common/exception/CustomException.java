package com.mink.freshexpress.common.exception;

import com.mink.freshexpress.common.exception.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    public String getMessage() {
        return errorCode.getMessage();
    }
}
