package com.example.demo.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPassword implements PasswordEncoder {
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println(encodedPassword);
        return encodedPassword.equals(rawPassword.toString());
    }
}
