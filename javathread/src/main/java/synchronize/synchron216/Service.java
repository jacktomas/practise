package synchronize.synchron216;

/**
 * Created by root on 17-3-3.
 */
public class Service {
    synchronized public void service1() {
        System.out.println("service 1");

        service2();
    }


    synchronized public void service2() {
        System.out.println("service 2");

        service3();
    }

    synchronized private void service3() {
        System.out.println("service 3");

    }
}
