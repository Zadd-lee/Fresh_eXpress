package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements ErrorCode{

    MULTIPLE_PARENT_CATEGORY(HttpStatus.BAD_REQUEST,"상위 카테고리가 여러개입니다." )
    , PARENT_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST,"존재하지 않는 상위 카테고리입니다." )
    , MULTIPLE_CATEGORY(HttpStatus.BAD_REQUEST,"이미 존재하는 카테고리입니다." )
    , NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다"),;
    private final HttpStatus httpStatus;
    private final String message;
}
