package aop;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/3/21.
 */
public class MyAdvice implements Advice {
    long beginTime = 0 ;
    public void beforeMethod(Method method) {
        System.out.println(method.getName()+" before at "+beginTime);
        beginTime = System.currentTimeMillis();
    }


    public void afterMethod(Method method) {
        long endTime = System.currentTimeMillis();
        System.out.println(method.getName()+" cost total "+ (endTime-beginTime));
    }
}
