package synchronize.synchron212;

/**
 * Created by root on 17-3-3.
 */
public class HasSelfPrivateNum {
    private int num = 0;

    synchronized public void addI(String userName) {
        if (userName.equals("a")) {
            num = 10;
            System.out.println("a set over");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            num = 200;
            System.out.println("b set over!");
        }
        System.out.println(userName + "  num=" + num);

    }
}
