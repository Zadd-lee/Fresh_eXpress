package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    ALREADY_SHIPPED(HttpStatus.BAD_REQUEST, "이미 출고된 주문입니다"),
    NOT_FOUND_RESERVATION(HttpStatus.INTERNAL_SERVER_ERROR,"주문이 생성되지 않았습니다. 관리자에게 문의하세요" );
    private final HttpStatus httpStatus;
    private final String message;
}
