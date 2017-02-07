package firstest;

import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by root on 17-2-7.
 */
public class CrawlingHandler implements CrawlingService.Iface {

    @Override
    public void write(List<Item> items) throws TException {
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }
}
