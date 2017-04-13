package com.test.observer;

import java.util.Vector;

/**
 * Created by root on 17-4-12.
 */
public abstract class Subject {
    private Vector<Observer> vector = new Vector<Observer>();

    public void addObserver(Observer observer) {
        vector.add(observer);
    }

    public void deleteObserver(Observer observer) {
        vector.remove(observer);
    }

    protected void notifyObeserver() {
        vector.stream().forEach(
                elem -> elem.update()
        );
    }

    public abstract void doSomething();
}
