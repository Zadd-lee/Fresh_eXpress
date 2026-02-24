package com.mink.freshexpress.product.constant;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.ProductErrorCode;
import lombok.Getter;

@Getter
public enum StorageTemp {
    ROOM,
    REFRIGERATED,
    FREEZE;

    public static StorageTemp of(String storageTemp) {
        for (StorageTemp storageTempEnum : StorageTemp.values()) {
            if (storageTempEnum.name().equalsIgnoreCase(storageTemp)) {
                return storageTempEnum;
            }
        }
        throw new CustomException(ProductErrorCode.STORAGE_TEMP_MISMATCH);
    }


}
