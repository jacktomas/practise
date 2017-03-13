package zookeepersession;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/13.
 */
public class SessionMetaData implements Serializable {
    private static final long serialVersionUID = -6446174402446690125L;

    /**
     * Session实例的ID
     */
    private String id;

    /**
     * session的创建时间
     */
    private Long createTm;

    /**
     * Session的最大空闲时间，默认情况下是30分钟。
     */
    private Long maxIdle;

    /**
     * Session的最后一次访问时间，每次调用Request.getSession方法时都会去更新这个值。用来计算当前Session是否超时。
     * 如果lastAccessTm+maxIdle小于System.currentTimeMillis()，就表示当前Session超时。
     */
    private Long lastAccessTm;

    /**
     * 当前Session是否可用，如果超时，则此属性为false。
     */
    private Boolean validate = false;

    /**
     * 为了冗余Znode的version值，用来实现乐观锁，对Session节点的元数据进行更新操作。
     */
    private int version = 0;

    /**
     * 构造方法
     */
    public SessionMetaData() {
        this.createTm = System.currentTimeMillis();
        this.lastAccessTm = this.createTm;
        this.validate = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Long createTm) {
        this.createTm = createTm;
    }

    public Long getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Long maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Long getLastAccessTm() {
        return lastAccessTm;
    }

    public void setLastAccessTm(Long lastAccessTm) {
        this.lastAccessTm = lastAccessTm;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
