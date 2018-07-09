package com.example;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 環境構築を簡単にするために、組み込みTomcatを利用しています。
 * （Spring Bootは使っていません！！！）
 * 通常のTomcatにデプロイする場合は、このクラスは作成しません。
 *
 *
 * 参考URL -> https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /*
     * TODO 3-40 このmain()メソッドを実行後、ブラウザで http://localhost:8080/sample にアクセスして以下の点を確認する
     * - 顧客が全件一覧表示されている
     * - CSSが適用されていること（表の一部がオレンジになっている）
     * - [新規追加へ]をクリックし、新規追加ができること
     * - 新規追加時に入力検証が実行されること
     */
    /*
     * TODO 4-27 このmain()メソッドを実行後、ブラウザで http://localhost:8080/sample にアクセスして以下の点を確認する
     * - ログイン画面にリダイレクトされる
     *     - 未ログイン時でもCSSが適用されている
     * - user@example.com/userでログインできる
     *     - 「ようこそ、userさん！」と表示される
     *     - [新規追加へ]のリンクが表示されない
     *     - ブラウザのURLバーに http://localhost:8080/sample/insertMain と入力するとエラー画面に遷移する
     * - admin@example.com/adminでログインできる
     *     - 「ようこそ、adminさん！」と表示される
     *     - [新規追加へ]のリンクが表示される
     *     - 顧客の新規追加ができる
     * - [ログアウト]ボタンをクリックすると、ログアウト後にログイン画面にリダイレクトされる
     * - 上記以外のメールアドレス/パスワードを入力すると、ログイン画面にリダイレクトされる
     */
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        String prefix = new File(".").getAbsolutePath().contains("01-spring5") ? "" : "01-spring5/";

        // ポート番号 = 8080
        tomcat.setPort(8080);
        // コンテキストルート = /sample
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/sample",
                new File(prefix + "src/main/webapp/").getAbsolutePath());

        File additionWebInfClasses = new File(prefix + "target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}