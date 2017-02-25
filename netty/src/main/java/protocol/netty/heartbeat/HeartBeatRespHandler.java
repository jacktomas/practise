package protocol.netty.heartbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import protocol.netty.nettymessage.Header;
import protocol.netty.nettymessage.MessageType;
import protocol.netty.nettymessage.NettyMessage;

/**
 * Created by root on 17-2-25.
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("Receive client heart beat message :--->" + message);
            NettyMessage heartBeat = buildHeartBeat();
            System.out.println("Send heart beat response message to client:-->" + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
