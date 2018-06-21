演習5 Spring Bootによる無設定化
======================================

# 使うプロジェクト
03-boot2

> このプロジェクトは[Spring Initializr](https://start.spring.io/)で雛形を作成後、02-spring5-answerのほぼ全てのファイルをコピーして作成しています。
> Spring Bootによってどの設定が不要になるのか（もしくは変わらず必要なのか）を確認しながらTODOを進めてください。

# 主に使うパッケージ
すべて

# TODO 5-01-1
[Applicationクラス](src/main/java/com/example/Application.java)は、Spring Bootの起点となるクラスです。
`@SpringBootApplication`アノテーションをクラスに付加してください。

# TODO 5-01-2
[pom.xml](pom.xml)を確認してください。各種Starterライブラリによって、指定するライブラリが少なくなっていることを確認してください（変更不要）。

# TODO 5-02
[DataSourceConfigクラス](src/main/java/com/example/persistence/config/DataSourceConfig.java)は、組み込みDataSourceをBean定義しています。
このBeanはAuto Configuration対象のため、Spring BootがBeanを作成します。
よってこのJava Configクラスは不要のため、ファイルごと削除してください。

# TODO 5-03
組み込みDBの初期化で利用するSQLファイルの文字コードは、[application.properties](src/main/resources/application.properties)に指定します。
下記のプロパティを追記してください。

```properties
spring.datasource.sql-script-encoding=utf-8
```

# TODO 5-04
[JdbcConfigクラス](src/main/java/com/example/persistence/config/JdbcConfig.java)は、Spring Data JDBC関連のBeanを定義しています。
コンポーネントスキャンは`@SpringBootApplication`によって行われるため、`@ComponentScan`を削除してください。

# TODO 5-05
`NamedParameterJdbcTemplate`のBeanはAuto Configuration対象のため、Spring BootがBeanを作成します。
このBeanを定義しているメソッドを削除してください。

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

# TODO 5-15
Spring Bootには、Actuatorという運用・管理に便利な機能があります。
有効化するために、下記のStarterを追加してください。

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

# TODO 5-16
Spring Boot 2からは、デフォルトでは一部のエンドポイント（`/info`と`/health`のみ）しか公開されなくなりました。
全てのエンドポイントを公開するために、下記のプロパティを[application.properties](src/main/resources/application.properties)に追記してください。

```properties
management.endpoints.web.exposure.include=*
```

# TODO 5-17
[SecurityConfigクラス](src/main/java/com/example/security/config/SecurityConfig.java)で、Actuatorエンドポイントのセキュリティ設定を行います。
Actuatorエンドポイントには、今回はcurlコマンドなどでアクセスするので、Basic認証を有効化します。
下記の処理を追記してください。

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        // TODO この行を追加！！！！！！
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers("/insert*").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.logout()
                .invalidateHttpSession(true)
                .permitAll();
    }
```

# TODO 5-18
Actuatorの`/actuator/**`というURLには、`ACTUATOR`ロールのみアクセスできるようにします。
下記の処理を追記してください。

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        http.httpBasic();
        http.authorizeRequests()
                // TODO この行を追加！！！！！！
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .mvcMatchers("/insert*").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.logout()
                .invalidateHttpSession(true)
                .permitAll();
    }

```

> `ACTUATOR`ロールのユーザーは、[data.sql](src/main/resources/data.sql)に定義済みです。
> （ユーザー名 = actuator、パスワード = actuator）

# TODO 5-19
[Applicationクラス](src/main/java/com/example/Application.java)のmain()メソッドを実行してください。
下記のcurlコマンドで、Actuatorの各エンドポイントにアクセスしてください。

- `/actuator` (Actuatorエンドポイント一覧)

```bash
$ curl -v -X GET -u actuator:actuator http://localhost:8080/actuator | jq
(ヘッダー等は省略)
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
    },
    "auditevents": {
      "href": "http://localhost:8080/actuator/auditevents",
      "templated": false
    },
    "beans": {
      "href": "http://localhost:8080/actuator/beans",
      "templated": false
    },
    "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
    },
    (中略)
    "mappings": {
      "href": "http://localhost:8080/actuator/mappings",
      "templated": false
    }
  }
}
```

- `/health` (アプリケーションの状態)

```bash
$ curl -v -X GET -u actuator:actuator http://localhost:8080/actuator/health | jq
(ヘッダー等は省略)
{
  "status": "UP"
}
```

- `/env` (環境変数など設定値の一覧)

```bash
$ curl -v -X GET -u actuator:actuator http://localhost:8080/actuator/env | jq
(ヘッダー等は省略)
{
  "activeProfiles": [],
  "propertySources": [
    {
      "name": "server.ports",
      "properties": {
        "local.server.port": {
          "value": 8080
        }
      }
    },
    {
      "name": "servletContextInitParams",
      "properties": {}
    },
    {
      "name": "systemProperties",
      "properties": {
        "com.sun.management.jmxremote.authenticate": {
          "value": "false"
        },
        "java.runtime.name": {
          "value": "Java(TM) SE Runtime Environment"
        },
        (中略)
    },
    {
      "name": "systemEnvironment",
      "properties": {
        "PATH": {
          "value": "/opt/local/bin:/opt/local/sbin:/Users/tadamasatoshi/.sdkman/candidates/gradle/current/bin:/usr/local/Cellar/git/2.14.1/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Java/JavaVirtualMachines/jdk1.8.0_162.jdk/Contents/Home/bin:/usr/local/sbin:/usr/local/Cellar/postgresql94/9.4.9_1/bin:/opt/local/bin",
          "origin": "System Environment Property \"PATH\""
        },
        "SDKMAN_VERSION": {
          "value": "5.1.18+191",
          "origin": "System Environment Property \"SDKMAN_VERSION\""
        },
        "JAVA_HOME": {
          "value": "/Library/Java/JavaVirtualMachines/jdk1.8.0_162.jdk/Contents/Home",
          "origin": "System Environment Property \"JAVA_HOME\""
        },
        (中略)
    },
    {
      "name": "applicationConfig: [classpath:/application.properties]",
      "properties": {
        "spring.datasource.sql-script-encoding": {
          "value": "utf-8",
          "origin": "class path resource [application.properties]:2:39"
        },
        "logging.level.org.springframework.jdbc.core.JdbcTemplate": {
          "value": "debug",
          "origin": "class path resource [application.properties]:5:58"
        },
        "management.endpoints.web.exposure.include": {
          "value": "*",
          "origin": "class path resource [application.properties]:8:43"
        },
        "logging.level.org.springframework.security": {
          "value": "trace",
          "origin": "class path resource [application.properties]:10:44"
        }
      }
    }
  ]
}
```

# Well done!
これですべての演習が完成しました。
もし時間に余裕がある場合は、[todo-option.md](todo-option.md)に取り組んでください。