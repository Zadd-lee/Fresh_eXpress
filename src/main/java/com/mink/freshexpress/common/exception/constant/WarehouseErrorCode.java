package com.mink.freshexpress.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WarehouseErrorCode implements ErrorCode{

    NOT_FOUND_WAREHOUSE(HttpStatus.NOT_FOUND, "존재하지 않는 창고 입니다"),
    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND,"존재하지 않는 보관 위치입니다"),
    MULTIPLE_WAREHOUSE_LOCATION(HttpStatus.BAD_REQUEST, "중복된 보관 위치 입니다." ),
    MULTIPLE_REQUEST_WAREHOUSE_LOCATION(HttpStatus.BAD_REQUEST, "중복된 보관 위치를 입력했습니다"),;
    private final HttpStatus httpStatus;
    private final String message;
}
