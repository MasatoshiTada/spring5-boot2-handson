package com.example.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCryptPasswordEncoderでパスワードをハッシュ化して表示するクラス。
 * 新しいパスワードを作りたいときに利用してください。
 */
public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("rawPassword"));
    }
}
