package com.health.care.analyzer.dto.auth;

import com.health.care.analyzer.entity.userEntity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> roles;
    private final Boolean isEnabled;

    public UserInfoDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.isEnabled = user.getIsEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
