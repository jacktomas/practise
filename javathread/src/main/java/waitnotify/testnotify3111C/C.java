package waitnotify.testnotify3111C;

/**
 * Created by root on 17-3-1.
 */
public class C {
    private MyStack myStack;

    public C(MyStack myStack) {
        this.myStack = myStack;
    }

    public void popService() {
        myStack.pop();
        System.out.println("pop=" + myStack.pop());
    }
}
