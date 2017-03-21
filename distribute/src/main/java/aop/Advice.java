package aop;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface Advice {
    void beforeMethod(Method method);

    void afterMethod(Method method);
}
