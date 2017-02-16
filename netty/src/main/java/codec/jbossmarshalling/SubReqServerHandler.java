package codec.jbossmarshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by root on 17-2-14.
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("jack".equalsIgnoreCase(req.getUserName())) {
            System.out.println("service accept client subscribe req :" + req.toString());
        }
        ctx.writeAndFlush(resp(req.getSubReqID()));
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed,3 days later ,sent to the designated address");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }
}
