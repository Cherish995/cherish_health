package com.cherish.health;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
public class JobApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-jobs.xml");
        System.in.read();
    }
}
