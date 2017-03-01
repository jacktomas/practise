package waitnotify.testnotify3111C;

/**
 * Created by root on 17-3-1.
 */
public class P {
    private MyStack myStack;

    public P(MyStack myStack) {
        this.myStack = myStack;
    }

    public void pushService() {
        myStack.push();
    }
}
