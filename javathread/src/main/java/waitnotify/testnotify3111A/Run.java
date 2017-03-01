package waitnotify.testnotify3111A;

/**
 * Created by root on 17-3-1.
 */
public class Run {
    public static void main(String[] args) {
        String lock = new String("");
        P p = new P(lock);
        C c = new C(lock);
        ThreadP threadP = new ThreadP(p);
        threadP.start();
        ThreadC threadC = new ThreadC(c);
        threadC.start();
    }
}
