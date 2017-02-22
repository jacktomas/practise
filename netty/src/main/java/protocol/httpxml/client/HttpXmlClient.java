package protocol.httpxml.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import protocol.httpxml.codec.HttpXmlRequestEncoder;
import protocol.httpxml.codec.HttpXmlResponseDecoder;
import protocol.httpxml.pojo.Order;

import java.net.InetSocketAddress;

/**
 * Created by root on 17-2-22.
 */
public class HttpXmlClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new HttpXmlClient().connect(port);
    }

    private void connect(int port) {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class).
                    option(ChannelOption.TCP_NODELAY, true).
                    handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));


                            socketChannel.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true));
                            socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            socketChannel.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());

                            socketChannel.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandle());
                        }
                    });


            ChannelFuture f = b.connect(new InetSocketAddress("localhost", port)).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            workerGroup.shutdownGracefully();

        }
    }


}
