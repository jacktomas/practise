import com.test.client.RpcRequest;
import com.test.server.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2017/4/10.
 */
public class NettyServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("request data is "+msg);
        RpcResponse response = new RpcResponse();
        RpcRequest request = (RpcRequest) msg;
        System.out.println("----recieve massage -----");
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcRequest request) throws Throwable {
        System.out.println(request.getRequestId());
        return request.getRequestId();
    }
}
