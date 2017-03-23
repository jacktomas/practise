package com.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by root on 17-3-23.
 */
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method method : cl.getDeclaredMethods()) {
            UseCase uc = method.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found use case :" + uc.id() + " " + uc.description());
            }
        }
        for (Integer i : useCases) {
            System.out.println("warning : Missing use case-" + i);

        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 47, 48, 49, 50);
        trackUseCases(useCases, PasswordUtils.class);
    }
}
