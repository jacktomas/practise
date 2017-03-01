package waitnotify.testnotify3111C;

/**
 * Created by root on 17-3-1.
 */
public class Run {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        P p = new P(myStack);
        C c = new C(myStack);
        P_Thread pThread = new P_Thread(p);
        C_Thread cThread = new C_Thread(c);
        pThread.start();
        cThread.start();
    }
}
