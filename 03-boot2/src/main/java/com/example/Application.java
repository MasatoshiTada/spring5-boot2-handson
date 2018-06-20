package com.example;

import com.example.web.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

// TODO 5-01-1 @SpringBootApplicationを付加する

public class Application {

    // TODO 5-14 main()メソッドを実行して、ブラウザから http://localhost:8080/ にアクセスする
    // TODO 5-17 main()メソッドを再実行して、curlコマンドでActuatorエンドポイントにアクセスする
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // TODO 5-10 LoggingFilterを登録する





}
