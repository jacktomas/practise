package protocol.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import protocol.netty.codec.NettyMessageDecoder;
import protocol.netty.codec.NettyMessageEncoder;
import protocol.netty.heartbeat.HeartBeatReqHandler;
import protocol.netty.loginauth.LoginAuthReqHandler;
import protocol.netty.nettymessage.NettyConstant;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 17-2-25.
 */
public class NettyClient {
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        new NettyClient().connect(NettyConstant.PORT, NettyConstant.LOCALIP);
    }

    private void connect(int port, String host) {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class).
                    option(ChannelOption.TCP_NODELAY, true).
                    handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            socketChannel.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());

                            socketChannel.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            socketChannel.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
                            socketChannel.pipeline().addLast("HeartbeatHandler", new HeartBeatReqHandler());

                        }
                    });


            ChannelFuture f = b.connect(
                    new InetSocketAddress(host, port), new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT))
                    .sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }


}
