package protocol.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import protocol.netty.codec.NettyMessageDecoder;
import protocol.netty.codec.NettyMessageEncoder;
import protocol.netty.heartbeat.HeartBeatReqHandler;
import protocol.netty.loginauth.LoginAuthRespHandler;
import protocol.netty.nettymessage.NettyConstant;

/**
 * Created by root on 17-2-25.
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {
        new NettyServer().run();
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)
                                throws Exception {
                            socketChannel.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            socketChannel.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());

                            socketChannel.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            socketChannel.pipeline().addLast("LoginAuthHandler", new LoginAuthRespHandler());
                            socketChannel.pipeline().addLast("HeartbeatHandler", new HeartBeatReqHandler());
                        }
                    });
            ChannelFuture future = b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
