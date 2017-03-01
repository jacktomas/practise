package waitnotify.testnotify313A;

/**
 * Created by root on 17-3-1.
 */
public class Test1 {
    public static void main(String[] args) {
        String lock = new String("");
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
