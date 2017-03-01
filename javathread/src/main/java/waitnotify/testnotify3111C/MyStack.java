package waitnotify.testnotify3111C;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-1.
 */
public class MyStack {
    private List list = new ArrayList();

    synchronized public void push() {
        if (list.size() == 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        list.add("anyString=" + Math.random());
        this.notify();
        System.out.println("push=" + list.size());
    }

    synchronized public String pop() {
        String returnValue = "";

        if (list.size() == 0) {
            System.out.println("pop操作中的: " + Thread.currentThread().getName() + " 线程呈现wait状态");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnValue = "" + list.get(0);
        list.remove(0);
        this.notify();
        System.out.println("pop=" + list.size());
        return returnValue;
    }
}
