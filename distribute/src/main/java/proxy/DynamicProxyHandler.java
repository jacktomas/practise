package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/3/20.
 */
public class DynamicProxyHandler implements InvocationHandler {
    private Object testProxy;

    public DynamicProxyHandler(Object proxy) {
        this.testProxy = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("this is proxy : " + proxy.getClass() + ",method : " + method + " ,args: " + args);
        if (args != null) {
            for (Object arg : args) {
                System.out.println("  "+arg);
            }
        }
        return method.invoke(testProxy, args);
    }
}
