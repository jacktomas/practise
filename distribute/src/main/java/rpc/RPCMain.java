package rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by hdfs on 2/28/17.
 */
public class RPCMain {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    RpcExporter.exporter("localhost", 8088);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //The client neither knows nor cares about the implementation, only the interface
        RpcImporter<EchoService> importer = new RpcImporter<EchoService>();
        //EchoService is interface
        EchoService echo = importer.importer(EchoServiceImp.class, new InetSocketAddress("localhost", 8088));
        System.out.println(echo.echo("hi ,are you ok"));

    }
}
