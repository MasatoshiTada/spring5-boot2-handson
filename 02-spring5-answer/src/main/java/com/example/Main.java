package com.example;

import org.apache.catalina.*;
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
 * 参考URL -> https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        String prefix = new File(".").getAbsolutePath().contains("02-spring5-answer") ? "" : "02-spring5-answer/";

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