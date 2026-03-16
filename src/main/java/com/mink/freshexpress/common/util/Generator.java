package com.mink.freshexpress.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Generator {
    public static String generateOrderNo() {
        String prefix = "fx";
        String yyyyMMdd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //todo cnt 부분 해결하기
        return prefix + yyyyMMdd;
    }
}
