package com.example.securingweb;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUser implements UserDetails {

    public MyUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "USER";
            }
        });
        return grantedAuthorities;
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
