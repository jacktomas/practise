package synchronize.synchronize225;

/**
 * Created by root on 17-3-3.
 */
public class Run {
    public static void main(String[] args) {
        ObjectService objectService = new ObjectService();

        ThreadA threadA = new ThreadA(objectService);
        threadA.setName("a");

        ThreadB threadB = new ThreadB(objectService);
        threadB.setName("b");

        threadA.start();
        threadB.start();
    }
}
