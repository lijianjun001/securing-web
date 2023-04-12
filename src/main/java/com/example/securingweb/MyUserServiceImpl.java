package com.example.securingweb;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserServiceImpl implements MyUserService{
    @Override
    public MyUser findByName(String username) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return new MyUser(username,new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5").encode("password"));
    }
}
