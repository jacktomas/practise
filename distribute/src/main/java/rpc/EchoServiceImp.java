package rpc;

/**
 * Created by hdfs on 2/28/17.
 */
public class EchoServiceImp implements EchoService {
    public String echo(String ping) {
        return ping != null ? ping + "---> I am ok " : "I am Ok.";
    }
}
