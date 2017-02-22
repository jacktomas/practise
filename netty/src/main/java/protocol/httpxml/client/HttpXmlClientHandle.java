package protocol.httpxml.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.httpxml.codec.HttpXmlRequest;
import protocol.httpxml.codec.HttpXmlResponse;
import protocol.httpxml.pojo.OrderFactory;

/**
 * Created by root on 17-2-22.
 */
public class HttpXmlClientHandle extends SimpleChannelInboundHandler<HttpXmlResponse> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        System.out.println("The client receive response of http header is : " + msg.getHttpResponse().headers().names());

        System.out.println("the client receive response of http body is : " + msg.getResult());
    }
}
