package proxy;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/3/20.
 */
public class SimpleDynamicProxy {
    public static void consumer(TestInterface testInterface) {
        testInterface.dosomething();
        testInterface.somethingElse("this is normal call");
    }

    public static void main(String[] args) {
        TestInterfaceImp testInterfaceImp = new TestInterfaceImp();
        consumer(testInterfaceImp);
        TestInterface proxyInstance = (TestInterface) Proxy.newProxyInstance(
                TestInterface.class.getClassLoader(),
                new Class[]{TestInterface.class},
                new DynamicProxyHandler(testInterfaceImp));
        proxyInstance.dosomething();
        proxyInstance.somethingElse("test param");
    }
}
