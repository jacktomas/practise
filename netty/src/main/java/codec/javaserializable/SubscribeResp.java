package codec.javaserializable;

import java.io.Serializable;

/**
 * Created by root on 17-2-14.
 */
public class SubscribeResp implements Serializable {
    private int subReqID;
    private int respCode;
    private String desc;

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp: subReqID=" + subReqID + ",respCode=" + respCode + ",desc=" + desc;
    }
}
