package com.mink.freshexpress.product.constant;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.ProductErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Temperature {
    ROOM("R"),
    REFRIGERATED("C"),
    FREEZE("F");

    private final String code;

    private static final Map<String, Temperature> LOOKUP;

    static {
        Map<String, Temperature> map = new HashMap<>();
        for (Temperature t : values()) {
            map.put(t.name().toLowerCase(), t);
            map.put(t.code.toLowerCase(), t);
        }
        LOOKUP = Collections.unmodifiableMap(map);
    }

    public static Temperature of(String temperature) {
        if (temperature == null || temperature.isBlank()) {
            throw new CustomException(ProductErrorCode.STORAGE_TEMP_MISMATCH);
        }
        Temperature result = LOOKUP.get(temperature.trim().toLowerCase());
        if (result == null) {
            throw new CustomException(ProductErrorCode.STORAGE_TEMP_MISMATCH);
        }
        return result;
    }
}