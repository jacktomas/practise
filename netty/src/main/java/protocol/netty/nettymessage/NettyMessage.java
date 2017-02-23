package protocol.netty.nettymessage;

/**
 * Created by root on 17-2-23.
 */
public final class NettyMessage {
    private Header header;
    private Object object;

    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Object getObject() {
        return object;
    }

    public final void setObject(Object object) {
        this.object = object;
    }
}
