演習2 Springによるビジネスロジック開発
======================================

# 使うプロジェクト
01-spring5

# 主に使うパッケージ
com.example.serviceパッケージ

# TODO 2-01
[CustomerServiceインタフェース](src/main/java/com/example/service/CustomerService.java)は、コントローラーから呼ばれる、ビジネスロジックのインタフェースです。
メソッドが2つ定義されていることを確認してください（変更不要）。

# TODO 2-02
[CustomerServiceImplクラス](src/main/java/com/example/service/impl/CustomerServiceImpl.java)は、ビジネスロジックインタフェースの実装クラスです。
`@Service`を付加して、ビジネスロジッククラスのBeanであることを示してください。

# TODO 2-03
`CustomerRepository`のBeanを、コンストラクタでDIします。下記のように、フィールドとコンストラクタを作成してください。
今回はコンストラクタはクラス内に1つのみですので、`@Autowired`は不要です。

```java
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
```

# TODO 2-04
各メソッドをトランザクション管理下にするには、`@Transactional`を付加します。
こうすると、メソッド開始直前にトランザクション開始処理が割り込まれます（AOP）。
検索メソッドに`@Transactional(propagation = Propagation.REQUIRED, readOnly = true)`を付加してください。
`readOnly = true`とすると、このメソッド内ではSELECT文しか発行できなくなります。

# TODO 2-05
`CustomerRepository`の`findAll()`を呼び出し、その戻り値をreturnしてください。
この`findAll()`は、Spring Dataの`CrudRepository`に定義されたメソッドです。

# TODO 2-06
追加メソッドに`@Transactional(propagation = Propagation.REQUIRED, readOnly = false)`を付加してください。
`readOnly = false`とすると、INSERT文などの変更系SQLも実行できるようになります。

# TODO 2-07
`CustomerRepository`の`save()`を呼び出し、その戻り値をreturnしてください。
この`save()`は、Spring Dataの`CrudRepository`に定義されたメソッドです。

# TODO 2-08
[ServiceConfigクラス](src/main/java/com/example/service/config/ServiceConfig.java)は、ビジネスロジックやトランザクションに関するJava Configクラスです。
クラスに`@Configuration` を付加してJava Configであることを示してください。

# TODO 2-09
@Serviceクラスをコンポーネントスキャンするアノテーションを付加します。
クラスに`@ComponentScan(basePackages = "com.example.service.impl")`を付加してください。

# TODO 2-10
トランザクション管理を有効化します。
クラスに`@EnableTransactionManagement`を付加してください。

# TODO 2-11
実際にトランザクション管理を行う`PlatformTransactionManager`のBeanを定義します。
メソッドに`@Bean`を付加してください。

# TODO 2-12
[ServiceConfigTestクラス](src/test/java/com/example/service/config/ServiceConfigTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[ServiceConfigクラス](src/main/java/com/example/service/config/ServiceConfig.java)の実装を見直してください。

# TODO 2-13
[CustomerServiceTestクラス](src/test/java/com/example/service/CustomerServiceTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[CustomerServiceImplクラス](src/main/java/com/example/service/impl/CustomerServiceImpl.java)の実装を見直してください。

# Well done!
これで演習2は完成です。
次の演習は[todo-3.md](todo-3.md)に書かれています。