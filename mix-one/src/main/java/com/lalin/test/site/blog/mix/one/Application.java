package com.lalin.test.site.blog.mix.one;

import com.lalin.test.site.blog.mix.one.Dao.SqlServerConn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @task:
 * @discrption:
 * @author: AereXu
 * @date: 2017/2/1 16:25
 * @version: 1.0.0
 */
@SpringBootApplication
public class Application {
   @Autowired
   public SqlServerConn sqlServerConn;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
     //   sqlServerConn serverConn = new sqlServerConn();

    //    sqlServerConn.getConnection();
    }
}
