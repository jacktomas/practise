package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/4/5.
 */
public class RPCBootstrap {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext spring = new ClassPathXmlApplicationContext("spring");
    }
}
