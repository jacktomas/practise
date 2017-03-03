package synchronize.synchron227;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-3-3.
 */
public class MyOneList {
    private List list = new ArrayList();

    synchronized public void add(String data) {
        list.add(data);
    }

    synchronized public int getSize() {
        return list.size();
    }
}
