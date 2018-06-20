package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * ログイン画面に遷移するコントローラー。
     */
    // 「GET /login」に対応させる
    @GetMapping("/login")
    public String loginPage() {
        // src/main/resources/templates/login.htmlに遷移させる
        return "login";
    }
}
