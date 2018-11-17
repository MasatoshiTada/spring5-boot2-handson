演習3 Spring MVCとThymeleafによるWebアプリ開発
==========================================

この演習ではSpring MVCとThymeleafでプレゼンテーション層を作成します。
コントローラーから演習2で作成したビジネスロジック層の呼び出し、その結果をThymeleafで作成したビューに渡します。
（今回の演習では、ビューは作成済みになっています）

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
残りのフィールドの成約は指定済みです。確認のみしてください（変更不要）。
- `lastName`フィールドは、必須入力かつ32文字以内
- `email`フィールドは、必須入力・128文字以内・Eメール形式
- `birthday`フィールドは、必須入力

> `@NotBlank`は文字列にのみ付加できます。`null`・空文字・半角スペースを検証エラーにできます。
> `@NotNull`は全てのデータ型に付加でき、`null`のみを検証エラーにできます。

# TODO 3-03
`birthday`フィールドは`java.time.LocalDate`型のため、日付のフォーマットを指定する必要があります。
フィールドに`@DateTimeFormat(pattern = "yyyy-MM-dd")`を付加してください。

# TODO 3-04
[CustomerControllerクラス](src/main/java/com/example/web/controller/CustomerController.java)は、顧客に関するコントローラークラスです。
コントローラーとしてBean定義するために、クラスに`@Controller`を付加してください。

# TODO 3-05
`CustomerService`のBeanを、コンストラクタでDIします。下記のように、フィールドとコンストラクタを作成してください。
今回はコンストラクタはクラス内に1つのみですので、`@Autowired`は不要です。

```java
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
```

# TODO 3-06
`index()`メソッドは、顧客一覧画面に対応するコントローラーメソッドです。
- メソッドに`@GetMapping("/")`を付加してください。
- 顧客を全件検索して、その結果を顧客一覧画面に渡します。下記の記述を追加してください。

```java
        Iterable<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
```

- ビューのパスの一部`"index"`をreturnしてください。
    - これは`src/main/resources/templates/index.html`を表します。
    - `src/main/resources/templates/`や`.html`は、後ほど定義する`ThymeleafViewResolver`が付け加えてくれます。

# TODO 3-07
`insertMain()`メソッドは、新規追加画面に対応するコントローラーです。
メソッドに`@GetMapping("/insertMain")`を付加していることを確認してください（変更不要）。
ビューのパスの一部`"insertMain"`をreturnしていることを確認してください（変更不要）。
これは`src/main/resources/templates/insertMain.html`を表します。

# TODO 3-08
`insertComplete()`メソッドは、顧客データの新規追加を行います。
- メソッドに`@PostMapping("/insertComplete")`を付加してください。
- このコントローラーメソッド実行前に、入力検証を実行する必要があります。引数`CustomerForm`に`@Validated`を付加してください。
- 入力検証でエラーが見つかった場合、`bindingResult.hasErrors()`が`true`を返します。その場合、新規追加画面に戻るよう、下記のように実装してください。

```java
        if (bindingResult.hasErrors()) {
            return "insertMain";
        }
```

- `CustomerService`の`save()`メソッドで、顧客データをDBに登録します。下記の処理を追加してください。

```java
        customerService.save(customer);
```

- 新規追加の完了後は、顧客一覧画面（`"/"`）にリダイレクトします。`"redirect:/"`をreturnしていることを確認してください（変更不要）。

# TODO 3-09
[MvcConfigクラス](src/main/java/com/example/web/config/MvcConfig.java)は、Spring MVCに必要なBeanを定義するJava Configクラスです。
クラスに`@Configuration` を付加されていることを確認してください（変更不要）。

# TODO 3-10
Spring MVCを有効化します。
クラスに`@EnableWebMvc`を付加してください。

# TODO 3-11
コントローラークラスをコンポーネントスキャンします。
クラスに`@ComponentScan(basePackages = {"com.example.web.controller"})`を付加されていることを確認してください（変更不要）。

# TODO 3-12
このJava Configで、より多くのSpring MVCに関する設定を行えるようにします。
クラスに`WebMvcConfigurer`インタフェースを実装してください。

> Spring 4までは、`WebMvcConfigurerAdapter`クラスを継承してJava Configクラスを作成していました。
> Spring 5はJDK 8ベースになったため、`WebMvcConfigurer`インタフェースに空のデフォルト実装が追加されました。これにより不要になったため、`WebMvcConfigurerAdapter`クラスは非推奨になりました。

# TODO 3-13
Thymeleafでビューの解決を行う`SpringResourceTemplateResolver`のBeanを定義しています。
- メソッドに`@Bean`を付加されていることを確認してください（変更不要）。
- 下記の記述を追加して、Thymeleafテンプレート（HTMLファイル）が保存されているフォルダ（src/main/resources/templates/）と拡張子を指定してください。
```java
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
```

# TODO 3-14
ThymeleafでHTMLの出力を行う`SpringTemplateEngine`のBeanを定義しています。
メソッドに`@Bean`を付加されていることを確認してください（変更不要）。
また、`setTemplateResolver()`メソッドで`SpringResourceTemplateResolver`がセットされていることを確認してください（変更不要）。

# TODO 3-15
Thymeleaf用のViewResolver実装である`ThymeleafViewResolver`のBeanを定義しています。
メソッドに`@Bean`を付加されていることを確認してください（変更不要）。
また、`setTemplateEngine()`メソッドで`SpringTemplateEngine`がセットされていることを確認してください（変更不要）。

# TODO 3-16
Spring MVCでは、DispatcherServletが全リクエストを受け取るので、CSSなど静的コンテンツへのリクエストが404 Not Foundになってしまいます。
それを防ぐため下記のメソッドを追加してください。

```java
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
    }
```

# TODO 3-17
プロパティファイルからメッセージを取得する`MessageSource`をBeanを定義しています。
- メソッドに`@Bean`を付加されていることを確認してください（変更不要）。
- 下記の記述を追加して、メッセージが記述されたプロパティファイルの名前を指定してください。このプロパティファイルは[src/main/resources直下](src/main/resources/messages.properties)にあります（記述済み）。

```java
        messageSource.setBasename("messages");
```

# TODO 3-18
[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)は、DispatcherServletをサーバーに登録するクラスです。
`AbstractAnnotationConfigDispatcherServletInitializer`クラスを継承してください。
これだけでDispatcherServletが登録されます。

# TODO 3-19
`getServletConfigClasses()`メソッドが、これまで作成した全Java Configを配列で返していることを確認してください（変更不要）。
このJava Configで定義されたBeanが、DispatcherServlet内のDIコンテナで管理されます。

# TODO 3-20
`getServletMappings()`メソッドは、DispatcherServletに対するurl-patternになります。
`"/"`を返していることを確認してください（変更不要）。これによって、DispatcherServletが全リクエストを捕捉するようにしています。

> `getRootConfigClasses()`・`getServletConfigClasses()`・`getServletMappings()`には`@Override`アノテーションが付いていませんが、
> `AbstractAnnotationConfigDispatcherServletInitializer`クラスで定義されたメソッドをオーバーライドしています。

# TODO 3-21
[MvcInitializerTestクラス](src/test/java/com/example/web/config/MvcInitializerTest.java)を実行してください。
後のTODOの関係上、テストメソッドが1つだけ実行されませんが、それ以外のテストメソッドがグリーンになれば成功です。
レッドになった場合、[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)の実装を見直してください。

> 実行しないテストメソッドには、`@Disabled`が付加されています。
> これはJUnit 5で定義されたアノテーションで、付加されたテストメソッドは実行されません。

# TODO 3-22
[MvcConfigTestクラス](src/test/java/com/example/web/config/MvcConfigTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[MvcConfigクラス](src/main/java/com/example/web/config/MvcConfig.java)の実装を見直してください。

# TODO 3-23
[CustomerControllerTestクラス](src/test/java/com/example/web/controller/CustomerControllerTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[CustomerControllerクラス](src/main/java/com/example/web/controller/CustomerController.java)の実装を見直してください。

# TODO 3-24
[index.html](src/main/resources/templates/index.html)は顧客一覧画面です。
顧客を表示するテーブルを確認してください（変更不要）。

# TODO 3-25
[insertMain.html](src/main/resources/templates/insertMain.html)は新規追加画面です。
入力欄や、検証エラーメッセージを表示する部分を確認してください（変更不要）。

# TODO 3-26
[web.xml](src/main/webapp/WEB-INF/web.xml)に設定された`CharacterEncodingFilter`を確認してください（変更不要）。
このフィルターはSpringが提供しているもので、リクエストパラメータへの文字コード設定を行います。

# TODO 3-27
[Mainクラス](src/main/java/com/example/Main.java)のmain()メソッドを実行後、ブラウザで http://localhost:8080/sample/ にアクセスして以下の点を確認してください。
- 顧客が全件一覧表示されている
- CSSが適用されていること（表の一部がオレンジになっている）
- [新規追加へ]をクリックし、新規追加ができること
- 新規追加時に入力検証が実行されること

この時点では、ログイン機能はありません。

# Well done!
これで演習3は完成です。
次の演習は[todo-4.md](todo-4.md)に書かれています。