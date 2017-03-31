package com.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/3/31.
 */
public class FullConsructors {
    private static Logger logger=Logger.getLogger(FullConsructors.class.getName());
    //定义一个抛出特定异常的方法
    public static void f() throws MyException {
        System.out.println("Throwing MyException from f()");
        throw new MyException();
    }
    //定义一个抛出特定异常的方法
    public static void g() throws MyException {
        System.out.println("Throwing MyExcetion from g()");
        throw new MyException("Orignated in g()");
    }

    public static void main(String[] args) {

        try {
            f();
        } catch (MyException e) {
            e.printStackTrace(System.out);
        }

        try {
            g();
        } catch (MyException e) {
            //e.printStackTrace(System.out);

            //把异常打印到日志里面，异常与日志信息
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));

//            System.out.println(stringWriter.toString());
            logger.info(stringWriter.toString());
        }

    }
}
