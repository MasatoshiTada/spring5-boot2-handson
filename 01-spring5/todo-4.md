演習4 Spring Securityによる認証・認可
======================================

# 使うプロジェクト
01-spring5

# 主に使うパッケージ
- com.example.securityパッケージ
- com.example.persistenceパッケージ
- src/main/webappフォルダ
- src/main/resources/templatesフォルダ

# TODO 4-01
Spring Securityはサーブレットフィルターベースで動作します。
Spring Securityの十数個のフィルターを、すべて管理しているフィルターが *springSecurityFilterChain* です。
これは既に[web.xml](src/main/webapp/WEB-INF/web.xml)のコメント内に記述済みなので、コメントを外してください。

# TODO 4-02
[SecurityConfig](src/main/java/com/example/security/config/SecurityConfig.java)は、Spring Securityに関する設定を記述するJava Configクラスです。
Java Configであることを示し、かつSpring Securityを有効化するために`@EnableWebSecurity`アノテーションを付加してください。

> `@EnableWebSecurity`アノテーションには`@Configuration`が含まれているため、`@Configuration`は付加しなくてOKです

# TODO 4-03
後ほど、`com.example.security.details`にクラスを作成します。このパッケージをコンポーネントスキャンするためのアノテーションを下記のように付加してください。

```java
@ComponentScan(basePackages = "com.example.security.details")
```

# TODO 4-04
`WebSecurityConfigurerAdapter`クラスを継承してください。

# TODO 4-05
CSSなどの静的コンテンツは、Spring Securityの保護対象から外します。下記のメソッドを追加してください。

```java
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**");
    }
```

# TODO 4-06
`configure(HttpSecurity)`をオーバーライドすると、認証認可設定を記述することができます。
下記のメソッドを追加してください。

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        http.authorizeRequests()
                .mvcMatchers("/insert*").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.logout()
                .invalidateHttpSession(true)
                .permitAll();
    }
```

# TODO 4-07
ユーザーのパスワードは、平文のままではなくハッシュ化してからDBに保存します。
ユーザーがログインする際は、入力された平文のパスワードをハッシュ化してから、DBに保存されたパスワード（ハッシュ化済み）と比較することで検証します。
`PasswordEncoder`をBean定義すればそれが使われます。`passwordEncoder()`メソッドに、Beanであることを示すアノテーションを付加してください。

# TODO 4-08
メソッド内で`BCryptPasswordEncoder`をnewしてreturnしてください。

# TODO 4-09
[AccountRepositoryインタフェース](src/main/java/com/example/persistence/repository/AccountRepository.java)は、DBのユーザー情報を取得します。
内容を確認してください（変更不要）。

# TODO 4-10
[AccountRepositoryImplクラス](src/main/java/com/example/persistence/repository/impl/AccountRepositoryImpl.java)は、`AccountRepository`実装クラスです。
`@Repository`を付加して、Beanであることを示してください。
このクラスは、既に[JdbcConfigクラス](src/main/java/com/example/persistence/config/JdbcConfig.java)でコンポーネントスキャン対象に指定済みです。

# TODO 4-11
`findByEmail()`メソッドでユーザー情報の検索を行っています。`NamedParameterJdbcTemplate`と`ResultSetExtractor`を利用しています。
内容を確認してください（変更不要）。

# TODO 4-12
[AccountDetailsクラス](src/main/java/com/example/security/details/AccountDetails.java)が、`UserDetails`インタフェースを実装していることを確認してください（変更不要）。
この`UserDetails`実装クラスが、Spring Securityが利用するログインユーザー情報になります。

# TODO 4-13
`getAccount()`メソッドが、`account`フィールドを返していることを確認してください（変更不要）。
このメソッドは、後ほど画面にログイン中のユーザー名を表示する際に使われます。

# TODO 4-14
`getUsername()`メソッドが、`account`の`email`を返すようにしてください。これが、ログイン時に入力するユーザー名を表します。

# TODO 4-15
`getPassword()`メソッドが、`account`の`password`を返すようにしてください。これが、ハッシュ化されたパスワードを表します。

# TODO 4-16
`getAuthorities()`メソッドが、`authorities`フィールドを返すようにしてください。これが、ユーザーが持つロールを表します。

# TODO 4-17
[AccountDetailsServiceクラス](src/main/java/com/example/security/details/AccountDetailsService.java)は、`AccountDetails`を返す`loadUserByUsername()`メソッドを持っています。
このクラスに`@Service`を付加して、ビジネスロジックのBeanであることを示してください。

# TODO 4-18
`UserDetailsService`インタフェースを実装していることを確認してください（変更不要）。

# TODO 4-19
`loadUserByUsername()`メソッド内で、`AccountDetails`をnewしてreturnしてください（コンストラクタにAccountを渡してください）。

# TODO 4-20
[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)の`getServletConfigClasses()`メソッドが返している配列に、`SecurityConfig.class`を追加してください。
追加後のメソッドは下記のようになります。

```java
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DataSourceConfig.class, JdbcConfig.class, ServiceConfig.class,
                MvcConfig.class, SecurityConfig.class};
    }
```

# TODO 4-21
[AccountDetailsServiceTestクラス](src/test/java/com/example/security/details/AccountDetailsServiceTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[AccountDetailsクラス](src/main/java/com/example/security/details/AccountDetails.java)や[AccountDetailsServiceクラス](src/main/java/com/example/security/details/AccountDetailsService.java)の実装を見直してください。

# TODO 4-22
[SecurityConfigTestクラス](src/test/java/com/example/security/config/SecurityConfigTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[SecurityConfigクラス](src/main/java/com/example/security/config/SecurityConfig.java)の実装を見直してください。

> 各configure()メソッドについては存在のみを確認しています。実装の正しさについては、後のTODOでブラウザから実行して確認します。

# TODO 4-23
[MvcInitializerTestクラス](src/test/java/com/example/web/config/MvcInitializerTest.java)を少し変更します。
テスト実行前に
- `getServletConfigClassesTest_withSpringSecurity()`メソッドをテスト対象にするために、@Disabledを削除してください
- `getServletConfigClassesTest()`メソッドをテスト対象から外すために、@Disabledを付加してください

上記の変更後、このクラスを実行してください。
テストメソッドが1つだけ実行されませんが、それ以外のテストメソッドがグリーンになれば成功です。
レッドになった場合、[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)の実装を見直してください。

> 演習3で実行したのは`getServletConfigClassesTest()`メソッドです。このメソッドは`SecurityConfig.class`が無い状態のテストなので、今回は実行対象から外しています。
> それに対して、今回実行している`getServletConfigClassesTest_withSpringSecurity()`メソッドは、`SecurityConfig.class`が有る状態でテストしています。

# TODO 4-24
[login.html](src/main/resources/templates/login.html)はログイン画面です。内容を確認してください（変更不要）。

> この画面に対応するコントローラーは[LoginControllerクラス](src/main/java/com/example/web/controller/LoginController.java)です。

# TODO 4-25
[index.html](src/main/resources/templates/index.html)に、ログイン中のユーザー名を表示させます。
下記の記述を追加してください。

```html
<p>ようこそ、<span sec:authentication="principal.account.name">John</span>さん！</p>
```

# TODO 4-26
`sec:authorize`属性を利用して、`ADMIN`ロールのみ追加画面へのリンクを表示させます。
下記の`sec:authorize`属性を追記してください。

```html
<a href="insertMain.html" th:href="@{insertMain}" sec:authorize="hasRole('ADMIN')">新規追加へ</a>
```

# TODO 4-27
[Mainクラス](src/main/java/com/example/Main.java)から実行します。
このmain()メソッドを実行後、ブラウザで http://localhost:8080/sample にアクセスして以下の点を確認してください。

- ログイン画面にリダイレクトされる
    - 未ログイン時でもCSSが適用されている
- user@example.com/userでログインできる
    - 「ようこそ、userさん！」と表示される
    - [新規追加へ]のリンクが表示されない
    - ブラウザのURLバーに http://localhost:8080/sample/insertMain と入力するとエラー画面に遷移する
- admin@example.com/adminでログインできる
    - 「ようこそ、adminさん！」と表示される
    - [新規追加へ]のリンクが表示される
    - 顧客の新規追加ができる
- [ログアウト]ボタンをクリックすると、ログアウト後にログイン画面にリダイレクトされる
- 上記以外のメールアドレス/パスワードを入力すると、ログイン画面にリダイレクトされる

# Well done!
これで演習4は完成です。
次の演習は[03-boot2/todo-5.md](../03-boot2/todo-5.md)に書かれています。