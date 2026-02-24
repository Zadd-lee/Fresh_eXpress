package com.mink.freshexpress.product.constant;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.ProductErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Unit {
    EA("개",'1'),
    BOX("박스",'2'),
    PACK("팩",'3'),
    SET("세트",'4'),
    BUNDLE("번들",'5'),
    TRAY("트레이",'6')
    ;

    private final String name;
    private final char code;
    public static Unit ofBySKUCode(String SKUCode) {
        if (SKUCode.length() < 14) {
            return Unit.EA;
        } else {
            for (Unit unit : Unit.values()) {
                if (unit.code==SKUCode.charAt(0)) {
                    return unit;
                }
            }
        }
        throw new CustomException(ProductErrorCode.UNIT_MISMATCH);
    }
}
