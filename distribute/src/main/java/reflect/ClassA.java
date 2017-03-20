package reflect;

/**
 * Created by root on 17-3-20.
 */
public class ClassA {
    public int i;
    private int j;

    public ClassA() {
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public ClassA(int i, int j) {
        this.i = i;
        this.j = j;
        System.out.println("i is : " + i + " " + "j is : " + j);
    }

    private void prin() {
        System.out.println("i is : " + i);
        System.out.println("j is : " + j);
    }

    void show(String string) {
        System.out.println(string);
    }

}
