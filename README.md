Spring 5 & Spring Boot 2ハンズオン
================================

# 課題：顧客管理アプリ
- 顧客の全件一覧表示・新規追加を行えるWebアプリ
    - 演習1〜4：Spring Bootを使わない構成
    - 演習5：Spring Bootにアプリを移植

# 技術構成
- Webフレームワーク : Spring MVC
- ビュー : Thymeleaf 3
- 入力検証 : Hibernate Validator 6
- データアクセス : Spring Data JDBC
- 認証認可 : Spring Security
- サーバー : 組み込みTomcat
- DB : H2（組み込みDB）

# プロジェクト構成
- 01-spring5 : 演習1〜4（穴埋め形式）
- 02-spring5-answer : 演習1〜4の解答例
- 03-boot2 : 演習5（穴埋め形式）
- 04-boot2-answer : 演習5の解答例
    
# ハンズオンの進め方：テスト駆動形式
- 番号付きTODOコメントの順に作業を進める
- いくつかJUnitテスト実行のTODOがあるので、
    - グリーンになれば次のTODOへ
    - レッドならば以前のTODOを見直す

# 開始前に

## 開発環境
- JDK 8
- IntelliJ IDEA または Spring Tool Suite （なるべく最新版）
    - 文字コードは全てUTF-8
- curlコマンド
- jqコマンド

## 完成形（02-spring5-answer）の確認
1. 02-spring5-answerのcom.example.Mainクラスを実行（組み込みTomcatが起動する）
2. ブラウザで http://localhost:8080/sample/ にアクセス
3. メールアドレス「admin@example.com」 パスワード「admin」でログイン
4. 顧客の新規追加を行ってみる（空のままで送信、入力して送信）

## pom.xmlの確認
01-spring5のpom.xmlを開き、内容を確認する（変更不要）

# ハンズオン手順
演習1 Spring Data JDBCによるデータアクセス（[todo-1.md](01-spring5/todo-1.md)）
演習2 Springによるビジネスロジック開発（[todo-2.md](01-spring5/todo-2.md)）
演習3 Spring MVCとThymeleafによるWebアプリ開発（[todo-3.md](01-spring5/todo-3.md)）
演習4 Spring Securityによる認証・認可（[todo-4.md](01-spring5/todo-4.md)）
演習5 Spring Bootによる無設定化（[todo-5.md](03-boot2/todo-5.md)）