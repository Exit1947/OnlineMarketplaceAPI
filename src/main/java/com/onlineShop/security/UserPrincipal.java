package com.onlineShop.security;

import com.onlineShop.models.Users.RolePrivilege.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPrincipal implements UserDetails {

    private String userId;
    private String email;
    private String password;
    private List<? extends GrantedAuthority> authorities;
    private Role role;

    public UserPrincipal() {
    }

    public UserPrincipal(final String userId, final String email, final String password,
                         final List<? extends GrantedAuthority> authorities, final Role role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.role = role;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
