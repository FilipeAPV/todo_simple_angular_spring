package com.mytodo.backend.security;

import com.mytodo.backend.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrentUser implements UserDetails {

    private UserModel user;

    public CurrentUser(UserModel userModel) {
        this.user = userModel;
    }

    Logger logger = LoggerFactory.getLogger(CurrentUserService.class);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoleModel().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        logger.info(user.getEmail() + " has the following authorities: " + authorities);

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

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
}
