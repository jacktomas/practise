package com.test;

/**
 * Created by Administrator on 2017/3/31.
 */
public class InheritingException  {
    public void f() throws SimpleException {
        System.out.println("thow simpleexception from f()");
        throw new SimpleException();
    }

    public static void main(String[] args) {
        InheritingException inheritingException = new InheritingException();
        try {
            inheritingException.f();
        } catch (SimpleException e) {
            System.out.println("this is my first exception");
            e.printStackTrace();
        }
    }
}
