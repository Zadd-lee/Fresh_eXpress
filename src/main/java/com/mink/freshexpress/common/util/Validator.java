package com.mink.freshexpress.common.util;

import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CategoryErrorCode;
import com.mink.freshexpress.common.exception.constant.ErrorCode;

import java.util.Optional;

public class Validator {
    public static <T> T valid(Optional<T> optional, ErrorCode errorCode) {
        return optional.orElseThrow(() -> new CustomException(errorCode));
    }
}
