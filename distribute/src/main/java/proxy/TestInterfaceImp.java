package proxy;

/**
 * Created by Administrator on 2017/3/20.
 */
public class TestInterfaceImp implements TestInterface {
    public TestInterfaceImp() {
        System.out.println(" this is testInterface object ");
    }


    public void dosomething() {
        System.out.println("this function 1 ");
    }


    public void somethingElse(String arg) {
        System.out.println("this is a real instance ");
        System.out.println("the param is function 2 : "+arg);

    }
}
