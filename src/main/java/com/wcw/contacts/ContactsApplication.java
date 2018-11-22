package com.wcw.contacts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author 王成伍
 * @date  2018-11-20 08:40
 *
 */
@SpringBootApplication
@MapperScan("com.wcw.contacts.dao") //将项目中对应的mapper类的路径加进来就可以了
@EnableTransactionManagement//开启事务管理
@EnableAutoConfiguration
@ComponentScan("com.wcw.contacts")
public class ContactsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
    }


}
