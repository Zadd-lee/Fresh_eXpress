package com.mink.freshexpress.auth.constant;

import com.mink.freshexpress.common.exception.CustomException;
import com.mink.freshexpress.common.exception.constant.UserErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Role {

    ADMIN("admin"),
    WAREHOUSE_MANAGER("warehouseManager"),
    PACKAGE_MANAGER("packageManager"),
    DRIVER("driver"),
    CUSTOMER("customer");



    private final String name;

    public static Role of(String roleName) throws IllegalArgumentException {
        for (Role role : values()) {
            if (role.getName().equals(roleName.toLowerCase())) {
                return role;
            }
        }

        throw new CustomException(UserErrorCode.INVALID_ROLE_NAME);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }

}
