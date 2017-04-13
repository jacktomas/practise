package com.test.observer;

/**
 * Created by root on 17-4-12.
 */
public class SubjectImp extends Subject {
    @Override
    public void doSomething() {
        System.out.println("jack learve here");
        notifyObeserver();
    }
}
