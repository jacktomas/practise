package codec.javaserializable;

import java.io.Serializable;

/**
 * Created by root on 17-2-14.
 */
public class SubscribeReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "SubscribeReq [subReqID= " + subReqID + ",userName= " + userName + ",productName=" + productName + ",phoneName="
                + phoneName + ",address=" + address;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int subReqID;
    private String userName;
    private String productName;
    private String phoneName;
    private String address;
}
