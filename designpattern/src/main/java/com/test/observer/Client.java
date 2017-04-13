package com.test.observer;

/**
 * Created by root on 17-4-12.
 */
public class Client {
    public static void main(String[] args) {
        ObserverImp observerImp = new ObserverImp();
        SubjectImp subjectImp = new SubjectImp();
        subjectImp.addObserver(observerImp);
        subjectImp.doSomething();
    }
}
