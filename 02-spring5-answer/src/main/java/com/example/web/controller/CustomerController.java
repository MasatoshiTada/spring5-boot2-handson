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

// TODO 3-07 コントローラークラスであることを示すアノテーションを付加する
@Controller
public class CustomerController {

    /** 必要があれば、デバッグ時のログ出力に使ってください */
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    // TODO 3-08 CustomerServiceをコンストラクタインジェクションする
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 社員一覧画面に遷移するコントローラーメソッド。
     */
    // TODO 3-09 アノテーションを付加して「GET /」に対応させる
    @GetMapping("/")
    public String index(Model model) {
        // TODO 3-10 顧客を全件検索して、遷移先の画面に「customers」という名前で渡す
        Iterable<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        // TODO 3-11 src/main/resources/templates/index.htmlに遷移する
        return "index";
    }

    /**
     * 社員追加画面に遷移するコントローラーメソッド。
     */
    // TODO 3-12 アノテーションを付加して「GET /insertMain」に対応させる
    @GetMapping("/insertMain")
    public String insertMain(Model model) {
        // フィールドが全てnullのフォームインスタンスを追加する
        model.addAttribute(CustomerForm.createEmptyForm());
        // TODO 3-13 src/main/resources/templates/insertMain.htmlに遷移する
        return "insertMain";
    }

    /**
     * 社員の追加を行うコントローラーメソッド。
     */
    // TODO 3-14 アノテーションを付加して「POST /insertComplete」に対応させる
    @PostMapping("/insertComplete")
    public String insertComplete(
            // TODO 3-15 Bean Validationを実行するアノテーションを付加する
            @Validated CustomerForm customerForm,
            BindingResult bindingResult) {
        // TODO 3-16 検証エラーがあればinsertMain.htmlに遷移する
        if (bindingResult.hasErrors()) {
            return "insertMain";
        }
        // フォームをエンティティに変換
        Customer customer = customerForm.convertToEntity();
        // TODO 3-17 顧客をDBに追加する
        customerService.save(customer);
        // TODO 3-18 「/」にリダイレクトする
        return "redirect:/";
    }
}
