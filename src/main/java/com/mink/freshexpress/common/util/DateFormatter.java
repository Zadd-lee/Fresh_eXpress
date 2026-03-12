package com.mink.freshexpress.common.util;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CommonErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static LocalDateTime getDateTime(String date) {
        LocalDateTime result;
        if (date.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            result = LocalDateTime.parse(date, formatter);

        } else if (date.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            result = LocalDate.parse(date, formatter).atStartOfDay();
        } else {
            throw new CustomException(CommonErrorCode.INVALID_DATEFORMAT);
        }
        return result;

    }
}
