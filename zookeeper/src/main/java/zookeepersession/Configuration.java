package zookeepersession;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Configuration {
    private String Servers;

    private Long TimeOut;

    private int PoolSize;

    public String getServers() {
        return Servers;
    }

    public void setServers(String servers) {
        Servers = servers;
    }

    public Long getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(Long timeOut) {
        TimeOut = timeOut;
    }

    public int getPoolSize() {
        return PoolSize;
    }

    public void setPoolSize(int poolSize) {
        PoolSize = poolSize;
    }
}
