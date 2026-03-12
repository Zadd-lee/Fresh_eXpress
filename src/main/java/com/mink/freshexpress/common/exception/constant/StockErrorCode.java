package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StockErrorCode implements ErrorCode{

    STORAGE_TEMP_MISMATCH(HttpStatus.BAD_REQUEST,"보관 장소와 물품의 보관 온도가 일치하지 않습니다." ),
    ALREADY_DISCARD(HttpStatus.BAD_REQUEST,"이미 폐기된 재고입니다." ),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 재고입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
