演習5 Spring Bootによる無設定化
======================================

# 使うプロジェクト
03-boot2

> このプロジェクトは[Spring Initializr](https://start.spring.io/)で雛形を作成後、02-spring5-answerのほぼ全てのファイルをコピーして作成しています。
> Spring Bootによってどの設定が不要になるのか（もしくは変わらず必要なのか）を確認しながらTODOを進めてください。

# 主に使うパッケージ
すべて

# TODO 5-01
[Applicationクラス](src/main/java/com/example/Application.java)は、Spring Bootの起点となるクラスです。
`@SpringBootApplication`アノテーションをクラスに付加してください。

# TODO 5-02
[pom.xml](pom.xml)を確認してください。各種Starterライブラリによって、指定するライブラリが少なくなっていることを確認してください（変更不要）。

# TODO 5-03
[DataSourceConfigクラス](src/main/java/com/example/persistence/config/DataSourceConfig.java)は、組み込みDataSourceをBean定義しています。
このBeanはAuto Configuration対象のため、Spring BootがBeanを作成します。
よってこのJava Configクラスは不要のため、ファイルごと削除してください。

# TODO 5-04
組み込みDBの初期化で利用するSQLファイルの文字コードは、[application.properties](src/main/resources/application.properties)に指定します。
下記のプロパティを追記してください。

```properties
spring.datasource.sql-script-encoding=utf-8
```

# TODO 5-05
[JdbcConfigクラス](src/main/java/com/example/persistence/config/JdbcConfig.java)は、Spring Data JDBC関連のBeanを定義しています。
コンポーネントスキャンは`@SpringBootApplication`によって行われます。
リポジトリインタフェースのスキャン、`NamedParameterJdbcTemplate`のBean定義などはSpring BootのAuto Configurationクラスによって行われます。
よってこのJava Configクラスは不要のため、ファイルごと削除してください。
`config`パッケージも不要なので削除してください。

# TODO 5-06
[ServiceConfigクラス](src/main/java/com/example/service/config/ServiceConfig.java)は、トランザクション関連の設定を記述しています。
これらは全てAuto Configuration対象のため、Spring BootがBeanを作成します。
よってこのJava Configクラスは不要のため、ファイルごと削除してください。
`config`パッケージも不要なので削除してください。

# TODO 5-07
[MvcConfigクラス](src/main/java/com/example/web/config/MvcConfig.java)は、Spring MVC関連の設定を記述しています。
これらは全てAuto Configuration対象のため、Spring BootがBeanを作成します。
よってこのJava Configクラスは不要のため、ファイルごと削除してください。

# TODO 5-08
[MvcInitializerクラス](src/main/java/com/example/web/config/MvcInitializer.java)は、DispatcherServletをサーバーに登録しています。
これはAuto Configuration対象のため、Spring Bootが行います。
よってこのクラスは不要のため、ファイルごと削除してください。
`config`パッケージも不要なので削除してください。

# TODO 5-09
[web.xml](src/main/webapp/WEB-INF/web.xml)では、サーブレットフィルターの登録を行っています。
`CharacterEncodingFilter`および`springSecurityFilterChain`はAuto Configuration対象のため、Spring Bootが登録を行います。
また、その他のフィルター（`LoggingFilter`など）は別の方法で登録を行います（次のTODO）。
よってこのXMLは不要のため、`src/main/webapp`フォルダごと削除してください。

# TODO 5-10
Spring Bootでは、`FilterRegistrationBean`をBean定義することでサーブレットフィルターを登録します。
[Applicationクラス](src/main/java/com/example/Application.java)に、下記の記述を追加してください。

```java
    @Bean
    public FilterRegistrationBean loggingFilter() {
        LoggingFilter loggingFilter = new LoggingFilter();
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>(loggingFilter);
        // フィルターの順番を一番最初に指定
        registrationBean.setOrder(Integer.MIN_VALUE);
        // url-patternを指定
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
```

# TODO 5-11
[SecurityConfigクラス](src/main/java/com/example/security/config/SecurityConfig.java)は、Spring Security関連の設定を記述しています。
Spring Boot 2.0からは、Security関連の設定はJava Configで記述するため、このクラスのほとんどの設定が必要です。
ただし、`@ComponentScan`のみ不要のため、削除してください。

# TODO 5-12
[logback.xml](src/main/resources/logback.xml)は、Logback関連の設定を記述しています。
Spring Bootでは、ログの設定はすべて[application.properties](src/main/resources/application.properties)に記述するため（次のTODO）、logback.xmlはファイルごと削除してください。

# TODO 5-13
[application.properties](src/main/resources/application.properties)に、ログレベルなどの設定を記述します。
下記を追記してください。

```properties
logging.level.org.springframework.jdbc.core.JdbcTemplate=debug
```

# TODO 5-14
[Applicationクラス](src/main/java/com/example/Application.java)のmain()メソッドを実行してください。
ブラウザから http://localhost:8080/ を開いて、01-spring5と同様の挙動であることを確認してください。
確認後は、アプリケーションを停止してください。

> Spring Bootで作成したWebアプリケーションには、コンテキストルート（01-spring5の`/sample`）は有りません。
> （application.propertiesで任意のコンテキストルートを付ける設定を記述することもできます）

# Well done!
これで演習5は完成です。
次の演習は[todo-6.md](todo-6.md)に書かれています。