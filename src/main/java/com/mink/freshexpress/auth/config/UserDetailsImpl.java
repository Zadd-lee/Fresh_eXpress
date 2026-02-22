package com.mink.freshexpress.auth.config;

import com.mink.freshexpress.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {
    private final User user;

    //todo 리팩터 필요
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(this.user.getRole().getAuthorities());
    }

    @Override
    public @Nullable String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }
}

