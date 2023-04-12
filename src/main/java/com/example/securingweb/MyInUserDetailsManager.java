package com.example.securingweb;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class MyInUserDetailsManager extends InMemoryUserDetailsManager {


    public MyInUserDetailsManager() {

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SpringUtils.getBean(MyUserService.class).findByName(username);
    }
}
