package com.test;

/**
 * Created by Administrator on 2017/3/31.
 * 自定义异常
 */

public class MyException extends Exception {
    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }
}
