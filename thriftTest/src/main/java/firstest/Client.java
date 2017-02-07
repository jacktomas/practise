package firstest;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-2-7.
 */
public class Client {
    public void write(List<Item> items) {
        TTransport transport;
        try {
            transport = new TSocket("localhost", 7911);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            CrawlingService.Client client = new CrawlingService.Client(protocol);

            client.write(items);
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Item item = new Item();
        item.setContent("this is test");
        item.setId(123);
        Client client = new Client();
        List items = new ArrayList();
        items.add(item);
        client.write(items);
    }
}

