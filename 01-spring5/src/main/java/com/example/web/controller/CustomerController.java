package com.example.web.controller;

import com.example.persistence.entity.Customer;
import com.example.service.CustomerService;
import com.example.web.form.CustomerForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// TODO 3-04 コントローラークラスであることを示すアノテーションを付加する

public class CustomerController {

    /** 必要があれば、デバッグ時のログ出力に使ってください */
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    // TODO 3-05 CustomerServiceをコンストラクタインジェクションする




    /**
     * 社員一覧画面に遷移するコントローラーメソッド。
     */
    // TODO 3-06 index()メソッドを作成する

    public String index(Model model) {
        // 顧客を全件検索して、その結果を顧客一覧画面に渡す


        // src/main/resources/templates/index.htmlに遷移する
        return null;
    }

    /**
     * 社員追加画面に遷移するコントローラーメソッド。
     */
    // TODO 3-07 insertMain()メソッドを確認する（変更不要）
    @GetMapping("/insertMain")
    public String insertMain(Model model) {
        // フィールドが全てnullのフォームインスタンスを追加する
        model.addAttribute(CustomerForm.createEmptyForm());
        // src/main/resources/templates/insertMain.htmlに遷移する
        return "insertMain";
    }

    /**
     * 社員の追加を行うコントローラーメソッド。
     */
    // TODO 3-08 insertComplete()メソッドを作成する

    public String insertComplete(
            // Bean Validationを実行するアノテーションを付加する
               CustomerForm customerForm,
            BindingResult bindingResult) {
        // 検証エラーがあればinsertMain.htmlに遷移する



        // フォームをエンティティに変換
        Customer customer = customerForm.convertToEntity();
        // 顧客をDBに追加する

        // 「/」にリダイレクトする
        return "redirect:/";
    }
}
