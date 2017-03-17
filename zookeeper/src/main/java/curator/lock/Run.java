package curator.lock;

/**
 * Created by root on 17-3-17.
 */
public class Run {
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new ThreadA().start();
        }
    }
}
