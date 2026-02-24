package com.mink.freshexpress.product.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Unit {
    EA("개"),
    BOX("박스"),
    PACK("팩"),
    SET("세트"),
    BUNDLE("번들"),
    TRAY("트레이")
    ;

    private final String name;
}
