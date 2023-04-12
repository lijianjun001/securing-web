package com.example.securingweb;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {

        return MD5Encoder.encode(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
