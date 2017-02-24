package protocol.netty.loginauth;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.netty.nettymessage.Header;
import protocol.netty.nettymessage.MessageType;
import protocol.netty.nettymessage.NettyMessage;

/**
 * Created by root on 17-2-24.
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    private NettyMessage buildLoginReq() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        nettyMessage.setHeader(header);
        return nettyMessage;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            Byte loginResult = (Byte) message.getObject();
            if (loginResult != (byte) 0) {
                ctx.close();
            } else {
                System.out.println("Login is OK : " + message);
                ctx.fireChannelRead(msg);
            }
        } else
            ctx.fireChannelRead(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
