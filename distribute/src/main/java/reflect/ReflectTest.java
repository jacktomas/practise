package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by root on 17-3-20.
 */
public class ReflectTest {
    public static void main(String[] args) {
        try {
            Class<ClassA> classA = (Class<ClassA>) Class.forName("reflect.ClassA");
            //打印类加载器
            System.out.println("class loader is : " + classA.getClassLoader());

            //打印接口信息
            for (Class aClass : classA.getInterfaces()) {
                System.out.println("this is interface : " + aClass);
            }

            //打印方法，只能打印public的
            System.out.println("get methods :  " + classA.getMethods().toString());
            for (Method method : classA.getMethods()) {
                System.out.println(method);
            }
            //print all methods
            System.out.println("=======print all methods ");
            for (Method method : classA.getDeclaredMethods()) {
                System.out.println(method);
            }
            //打印构造器
            for (Constructor<?> constructor : classA.getConstructors()) {
                System.out.println(constructor);
            }

            //打印成员变量，只能打印public的
            for (Field field : classA.getFields()) {
                System.out.println("this is fields");
                System.out.println(field);
            }

            //反射调用方法
            try {
                System.out.println("call method ");
                ClassA a = classA.newInstance();
                a.show("this is reflect test");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //打印私有成员变量
            try {
                System.out.println(classA.getDeclaredField("j"));
                Field j = classA.getDeclaredField("j");
                classA.getDeclaredMethods();
                j.setAccessible(true);
                try {
                    System.out.println(j.getInt(new ClassA(1, 2)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            try {
                Method prin = classA.getDeclaredMethod("prin");
                try {
                    prin.setAccessible(true);
                    System.out.println("======invoke private method =======");
                    prin.invoke(new ClassA(3, 4), null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            //通过获取的构造器实例化对象
            Constructor<?>[] declaredConstructors = classA.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                try {
                    System.out.println("this is data create instance ");
                    if (constructor.getParameterTypes().length > 0) {
                        ClassA o = (ClassA) constructor.newInstance(5, 6);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
