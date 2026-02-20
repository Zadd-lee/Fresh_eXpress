package com.mink.freshexpress.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.ExceptionDto;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(@JsonIgnore
                             HttpStatus httpStatus,
                             @Nullable T data,
                             @Nullable ExceptionDto error) {
    public static <T> ApiResponse<T> ok(@Nullable final T data) {
        return new ApiResponse<>(HttpStatus.OK, data, null);
    }

    public static <T> ApiResponse<T> created(@Nullable final T data) {
        return new ApiResponse<>(HttpStatus.CREATED, data, null);
    }

    public static <T> ApiResponse<T> fail(final CustomException e) {
        return new ApiResponse<>(e.getErrorCode().getHttpStatus(), null, ExceptionDto.of(e.getErrorCode()));
    }

}
