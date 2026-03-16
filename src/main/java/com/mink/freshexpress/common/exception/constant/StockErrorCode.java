package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StockErrorCode implements ErrorCode{

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 재고입니다."),
    STORAGE_TEMP_MISMATCH(HttpStatus.BAD_REQUEST, "재고와 보관 위치의 온도가 일치하지 않습니다"),
    ALREADY_DISCARD(HttpStatus.BAD_REQUEST, "이미 삭제된 재고 입니다"),
    OVERSTOCK_REQUEST(HttpStatus.BAD_REQUEST, "현재 존재하는 재고보다 많은 요청을 했습니다");
    private final HttpStatus httpStatus;
    private final String message;
}
