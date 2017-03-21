package aop;

import java.io.InputStream;
import java.util.Collection;

/**
 * Created by Administrator on 2017/3/21.
 */
public class AopFrameWorkTest {
    public static void main(String[] args) {
        InputStream inputStream = AopFrameWorkTest.class.getResourceAsStream("/conf");
        Object bean = new BeanFactory(inputStream).getBean("xxx");
        System.out.println(bean.getClass().getName());
        ((Collection) bean).clear();
    }
}
