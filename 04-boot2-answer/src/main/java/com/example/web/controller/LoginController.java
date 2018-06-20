package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * ログイン画面に遷移するコントローラー。
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
