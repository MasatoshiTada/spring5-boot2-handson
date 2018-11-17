演習6 Spring Boot Actuatorによる運用監視
======================================

この演習では、Spring BootのActuator機能を実装します。

## TODO 6-01
Spring Bootには、Actuatorという運用・管理に便利な機能があります。
有効化するために、[pom.xml](pom.xml)に下記のStarterを追加してください。

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

## TODO 6-02
Spring Boot 2からは、デフォルトでは一部のエンドポイント（`/info`と`/health`のみ）しか公開されなくなりました。
全てのエンドポイントを公開するために、下記のプロパティを[application.properties](src/main/resources/application.properties)に追記してください。

```properties
management.endpoints.web.exposure.include=*
```

## TODO 6-03
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

## TODO 6-04
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

> `EndpointRequest`クラスは2つありますので注意してください。
> 今回利用するのは`org.springframework.boot.actuate.autoconfigure.security.servlet`のものです。

> `ACTUATOR`ロールのユーザーは、[data.sql](src/main/resources/data.sql)に定義済みです。
> （ユーザー名 = actuator、パスワード = actuator）

## TODO 6-05
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
これで演習6は完成です。
すべての演習が終了しました。お疲れ様でした！