package synchronize.synchron217;

/**
 * Created by root on 17-3-3.
 */
public class Test {
    public static void main(String[] args) {

        Service service = new Service();

        ThreadA threadA = new ThreadA(service);
        ThreadB threadB = new ThreadB(service);

        threadA.setName("a");
        threadB.setName("b");

        threadA.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadB.start();

    }
}
