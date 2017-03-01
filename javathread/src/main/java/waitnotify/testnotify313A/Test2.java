package waitnotify.testnotify313A;

/**
 * Created by root on 17-3-1.
 */
public class Test2 {
    public static void main(String[] args) {
        String lock = new String();
        System.out.println("sysn上面");
        synchronized (lock) {
            System.out.println("syn 第一行");
            try {
                lock.wait();
                System.out.println("wait 下的代码");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
