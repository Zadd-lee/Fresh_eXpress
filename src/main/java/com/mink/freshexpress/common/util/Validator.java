package com.mink.freshexpress.common.util;

import com.mink.freshexpress.category.model.Category;
import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.CategoryErrorCode;

import java.util.Optional;

public class Validator {
    public static Category valid(Optional<Category> category) {
        return category.orElseThrow(() -> new CustomException(CategoryErrorCode.NOT_FOUND));
    }

    public static Category validParents(Optional<Category> parentCategory) {
        return parentCategory.orElseThrow(() -> new CustomException(CategoryErrorCode.PARENT_CATEGORY_NOT_FOUND));
    }
}
