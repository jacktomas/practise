package synchronize.synchron227;

/**
 * Created by root on 17-3-3.
 */
public class Run {
    public static void main(String[] args) {
        MyOneList myOneList = new MyOneList();
        MyThread1 myThread1 = new MyThread1(myOneList);
        MyThread2 myThread2 = new MyThread2(myOneList);

        myThread1.setName("A");
        myThread2.setName("B");

        myThread1.start();
        myThread2.start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("listSize =  " + myOneList.getSize());
    }
}
