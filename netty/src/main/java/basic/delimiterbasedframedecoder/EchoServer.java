package basic.delimiterbasedframedecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by root on 17-2-14.
 */
public class EchoServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        new EchoServer().bind(port);
    }

    private void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class).
                    option(ChannelOption.SO_BACKLOG, 1024).
                    childHandler(new ChildChannelHandler());


            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            bossGroup.shutdownGracefully();

            workerGroup.shutdownGracefully();

        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new EchoServerHandler());


        }
    }
}
