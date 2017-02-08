package firstest;

import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.NiftyBootstrap;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.facebook.nifty.guice.NiftyModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import org.apache.thrift.TProcessor;

/**
 * Created by root on 17-2-8.
 */
public class ServerNitty {
    public void startServer() {
        //create your handler
        CrawlingService.Iface handler = new CrawlingHandler();

        //create the processor
        TProcessor processor = new CrawlingService.Processor<CrawlingService.Iface>(handler);

        // Build the server definition
        ThriftServerDef serverDef = new ThriftServerDefBuilder().withProcessor(processor).listen(7911)
                .build();

        // Create the server transport
        final NettyServerTransport server = new NettyServerTransport(serverDef);

        // Start the server
        server.start();

        // Arrange to stop the server at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void startGuiceServer() {
        final NiftyBootstrap bootstrap = Guice.createInjector(
                Stage.PRODUCTION,
                new NiftyModule() {
                    @Override
                    protected void configureNifty() {
                        // Create the handler
                        CrawlingService.Iface serviceInterface = new CrawlingHandler();


                        // Create the processor
                        TProcessor processor = new CrawlingService.Processor<CrawlingService.Iface>(serviceInterface);


                        // Build the server definition
                        ThriftServerDef serverDef = new ThriftServerDefBuilder().listen(7911).withProcessor(processor)
                                .build();

                        // Bind the definition
                        bind().toInstance(serverDef);
                    }
                }).getInstance(NiftyBootstrap.class);

        // Start the server
        bootstrap.start();

        // Arrange to stop the server at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                bootstrap.stop();
            }
        });
    }

    public static void main(String args[]) {
        ServerNitty server = new ServerNitty();
//      server.startGuiceServer();
        server.startServer();


    }
}
