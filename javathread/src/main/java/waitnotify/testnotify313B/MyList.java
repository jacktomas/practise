package waitnotify.testnotify313B;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-1.
 */
public class MyList {
    private static List list = new ArrayList();

    public static void add() {
        list.add("first head");

    }

    public static int size() {
        return list.size();
    }
}
