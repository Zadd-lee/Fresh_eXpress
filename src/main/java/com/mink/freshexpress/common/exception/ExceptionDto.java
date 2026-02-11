package com.mink.freshexpress.common.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionDto {
    @NotNull
    private final HttpStatus httpStatus;
    @NotNull
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
