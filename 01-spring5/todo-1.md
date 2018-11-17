演習1 Spring Data JDBCによるデータアクセス
======================================

この演習では、Spring Data JDBCを利用して永続化層を作成します。

# 使うプロジェクト
01-spring5

# 主に使うパッケージ
com.example.persistenceパッケージ

# TODO 1-01, 1-02
DB初期化に使う[schema.sql](src/main/resources/schema.sql)と[data.sql](src/main/resources/data.sql)の内容を確認してください（変更不要）。
customerテーブルが顧客データです。

# TODO 1-03
[DataSourceConfigクラス](src/main/java/com/example/persistence/config/DataSourceConfig.java)は、DataSourceのBeanを定義するJava Configクラスです。
クラスに`@Configuration` を付加してJava Configであることを示してください。

# TODO 1-04
dataSource()メソッドは、DataSourceのBeanを定義しています。
`@Bean` を付加して戻り値がBeanであることを示してください。
このBeanは組み込みDBのH2を使っており、起動時に[schema.sql](src/main/resources/schema.sql)と[data.sql](src/main/resources/data.sql)を使ってDB初期化しています。

# TODO 1-05
[DataSourceConfigTestクラス](src/test/java/com/example/persistence/config/DataSourceConfigTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[DataSourceConfigクラス](src/main/java/com/example/persistence/config/DataSourceConfig.java)の実装を見直してください。

# TODO 1-06
[Customerクラス](src/main/java/com/example/persistence/entity/Customer.java)は、customerテーブルに対応するエンティティクラスです。
主キーとなる`id`フィールドには、`@Id`アノテーションを付加してください。

# TODO 1-07
[CustomerRepositoryインタフェース](src/main/java/com/example/persistence/repository/CustomerRepository.java)は、customerテーブルへのCRUD操作を行います。
`CrudRepository<Customer, Integer>`インタフェースを継承してください。

# TODO 1-08
[JdbcConfigクラス](src/main/java/com/example/persistence/config/JdbcConfig.java)は、Spring Data JDBCに関するBean定義を行うJava Configです。
クラスに`@Configuration` を付加してJava Configであることを示してください。

# TODO 1-09
Spring Data JDBCを有効化するとともに、作成したCrudRepository継承インタフェースのパッケージを指定する必要があります。
クラスに`@EnableJdbcRepositories(basePackages = "com.example.persistence.repository")`を付加してください。

# TODO 1-10
Spring Data JDBCに関するBean定義のほとんどは`JdbcConfiguration`クラスに定義されています。このクラスを継承してください。

# TODO 1-11
Spring Data JDBCが内部で利用する`NamedParameterJdbcTemplate`をBean定義しています。このメソッドに`@Bean`を付加してください。

# TODO 1-12
[JdbcConfigTestクラス](src/test/java/com/example/persistence/config/JdbcConfigTest.java)を実行してください。
テストがグリーンになれば成功です。レッドになった場合、[JdbcConfigクラス](src/main/java/com/example/persistence/config/JdbcConfig.java)の実装を見直してください。

# TODO 1-13
[CustomerRepositoryTestクラス](src/test/java/com/example/persistence/repository/CustomerRepositoryTest.java)のクラス内コメントをすべて外してから、実行してください。
テストがグリーンになれば成功です。レッドになった場合、[CustomerRepositoryインタフェース](src/main/java/com/example/persistence/repository/CustomerRepository.java)の実装を見直してください。

# Well done!
これで演習1は完成です。
次の演習は[todo-2.md](todo-2.md)に書かれています。