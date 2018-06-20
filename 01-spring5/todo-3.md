演習3 Spring MVCとThymeleafによるWebアプリ開発
==========================================

# 使うプロジェクト
01-spring5

# 主に使うパッケージ
- com.example.webパッケージ
- src/main/webappフォルダ
- src/main/resources/templatesフォルダ

# TODO 3-01
[CustomerFormクラス](src/main/java/com/example/web/form/CustomerForm.java)は、顧客新規追加の際に入力データを受け取るクラスです。
`firstName`フィールドは、必須入力かつ32文字以内という制約があります。
フィールドに`@NotBlank` と `@Length(min = 1, max = 32)`を付加してください。

# TODO 3-02
`lastName`フィールドは、必須入力かつ32文字以内という制約があります。
フィールドに`@NotBlank` と `@Length(min = 1, max = 32)`を付加してください。

# TODO 3-03
`email`フィールドは、必須入力・128文字以内・Eメール形式という制約があります。
フィールドに`@NotBlank` 、 `@Length(min = 1, max = 32)` 、 `@Email` を付加してください。

# TODO 3-04
`birthday`フィールドは、必須入力という制約があります。
フィールドに`@NotNull`を付加してください。

# TODO 3-05
`birthday`フィールドは`java.time.LocalDate`型のため、日付のフォーマットを指定する必要があります。
フィールドに`@DateTimeFormat(pattern = "yyyy-MM-dd")`を付加してください。

# TODO 3-06
今回、このフォームクラスはイミュータブルになっています。この場合、コンストラクタの引数にも日付のフォーマットを指定する必要があります。
コンストラクタの引数`birthday`に`@DateTimeFormat(pattern = "yyyy-MM-dd")`を付加してください。

> イミュータブルなフォームクラスは、Spring 5からの機能です。ただし現時点ではエラーメッセージが正しく出力されないなどの不具合があります（Spring 5.1で修正予定です）。
> 参考URL -> https://www.slideshare.net/masatoshitada7/reactivespring-5-spring-boot-2/28

# TODO 3-07
[CustomerControllerクラス](src/main/java/com/example/web/controller/CustomerController.java)は、顧客に関するコントローラークラスです。
コントローラーとしてBean定義するために、クラスに`@Controller`を付加してください。

# TODO 3-08
`CustomerService`のBeanを、コンストラクタでDIします。下記のように、フィールドとコンストラクタを作成してください。
今回はコンストラクタはクラス内に1つのみですので、`@Autowired`は不要です。

```java
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
```

# TODO 3-09
`index()`メソッドは、顧客一覧画面に対応するコントローラーメソッドです。
メソッドに`@GetMapping("/")`を付加してください。

# TODO 3-10
顧客を全件検索して、その結果を顧客一覧画面に渡してください。

```java
        Iterable<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
```

# TODO 3-11
ビューのパスの一部`"index"`をreturnしてください。
これは`src/main/resources/templates/index.html`を表します。
`src/main/resources/templates/`や`.html`は、後ほど定義する`ThymeleafViewResolver`が付け加えてくれます。

# TODO 3-12
`insertMain()`メソッドは、新規追加画面に対応するコントローラーです。
メソッドに`@GetMapping("/insertMain")`を付加してください。

# TODO 3-13
ビューのパスの一部`"insertMain"`をreturnしてください。
これは`src/main/resources/templates/insertMain.html`を表します。

# TODO 3-14
`insertComplete()`メソッドは、顧客データの新規追加を行います。
メソッドに`@PostMapping("/insertComplete")`を付加してください。

# TODO 3-15
このコントローラーメソッド実行前に、入力検証を実行する必要があります。
引数`CustomerForm`に`@Validated`を付加してください。

# TODO 3-16
入力検証でエラーが見つかった場合、`bindingResult.hasErrors()`が`true`を返します。
その場合、新規追加画面に戻るように実装してください。

```java
        if (bindingResult.hasErrors()) {
            return "insertMain";
        }
```

# TODO 3-17
`CustomerService`の`save()`メソッドで、顧客データをDBに追加してください。

# TODO 3-18
新規追加の完了後は、顧客一覧画面にリダイレクトします。
`"redirect:/"`をreturnしてください。

# TODO 3-19
[MvcConfigクラス](src/main/java/com/example/web/config/MvcConfig.java)は、Spring MVCに必要なBeanを定義するJava Configクラスです。
クラスに`@Configuration` を付加してJava Configであることを示してください。

# TODO 3-20
Spring MVCを有効化します。
クラスに`@EnableWebMvc`を付加してください。

# TODO 3-21
コントローラークラスをコンポーネントスキャンします。
クラスに`@ComponentScan(basePackages = {"com.example.web.controller"})`を付加してください。

# TODO 3-22
このJava Configで、より多くのSpring MVCに関する設定を行えるようにします。
クラスに`WebMvcConfigurer`インタフェースを実装してください。

# TODO 3-23
Thymeleafでビューの解決を行う`SpringResourceTemplateResolver`のBeanを定義します。
メソッドに`@Bean`を付加してください。

# TODO 3-24
Thymeleafテンプレート（HTMLファイル）が保存されているフォルダ（src/main/resources/templates/）を下記のように指定してください。

```java
        templateResolver.setPrefix("classpath:/templates/");
```

# TODO 3-25
Thymeleafテンプレート（HTMLファイル）の拡張子を下記のように指定してください。

```java
        templateResolver.setSuffix(".html");
```

# TODO 3-26
ThymeleafでHTMLの出力を行う`SpringTemplateEngine`のBeanを定義します。
メソッドに`@Bean`を付加してください。

# TODO 3-27
Thymeleaf用のViewResolver実装である`ThymeleafViewResolver`をBean定義します。
メソッドに`@Bean`を付加してください。

# TODO 3-28
Spring MVCでは、DispatcherServletが全リクエストを受け取るので、CSSなど静的コンテンツへのリクエストが404 Not Foundになってしまいます。
それを防ぐためには、addResourceHandlers()を下記のようにオーバーライドしてください。

```java
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }
```

# TODO 3-29
プロパティファイルからメッセージを取得する`MessageSource`をBean定義します。
メソッドに`@Bean`を付加してください。

# TODO 3-30
メッセージが記述されたプロパティファイルの名前を下記のように指定してください。
このプロパティファイルは[src/main/resources直下](src/main/resources/messages.properties)にあります（記述済み）。

```java
        messageSource.setBasename("messages");
```

# TODO 3-31
[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)は、DispatcherServletをサーバーに登録するクラスです。
`AbstractAnnotationConfigDispatcherServletInitializer`クラスを継承してください。
これだけでDispatcherServletが登録されます。

# TODO 3-32
getServletConfigClasses()をオーバーライドして、これまで作成した全Java Configを配列で返してください。
このJava Configで定義されたBeanが、DispatcherServlet内のDIコンテナで管理されます。

```java
        return new Class[]{DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class,
                MvcConfig.class};
```

# TODO 3-33
getServletMappings()は、DispatcherServletに対するurl-patternになります。
`"/"`を返して、全リクエストを捕捉するようにしてください。

```java
        return new String[]{ "/" };
```

# TODO 3-34
[MvcInitializerTestクラス](src/test/java/com/example/web/config/MvcInitializerTest.java)を、クラス内のコメントを外してから実行してください。
テストがグリーンになれば成功です。レッドになった場合、[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)の実装を見直してください。

# TODO 3-35
[MvcConfigTestクラス](src/test/java/com/example/web/config/MvcConfigTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[MvcConfigクラス](src/main/java/com/example/web/config/MvcConfig.java)の実装を見直してください。

# TODO 3-36
[CustomerControllerTestクラス](src/test/java/com/example/web/controller/CustomerControllerTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[CustomerControllerクラス](src/main/java/com/example/web/controller/CustomerController.java)の実装を見直してください。

# TODO 3-37
[index.html](src/main/resources/templates/index.html)は顧客一覧画面です。
顧客を表示するテーブルを確認してください（変更不要）。

# TODO 3-38
[insertMain.html](src/main/resources/templates/insertMain.html)は新規追加画面です。
入力欄や、検証エラーメッセージを表示する部分を確認してください（変更不要）。

# TODO 3-39
[web.xml](src/main/webapp/WEB-INF/web.xml)に設定された文字コードフィルターを確認してください（変更不要）。

# TODO 3-40
[Mainクラス](src/main/java/com/example/Main.java)のmain()メソッドを実行後、ブラウザで http://localhost:8080/sample/ にアクセスして以下の点を確認してください。
- 顧客が全件一覧表示されている
- CSSが適用されていること（表の一部がオレンジになっている）
- [新規追加へ]をクリックし、新規追加ができること
- 新規追加時に入力検証が実行されること

この時点では、ログイン機能はありません。

# Well done!
これで演習3は完成です。
次の演習は[todo-4.md](todo-4.md)に書かれています。