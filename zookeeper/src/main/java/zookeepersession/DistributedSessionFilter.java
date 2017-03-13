package zookeepersession;

import org.eclipse.jetty.server.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.*;
import java.io.IOException;
import javax.servlet.FilterConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/3/13.
 */
public class DistributedSessionFilter implements Filter {

    protected Configuration conf;

    /**
     * Session管理器
     */
    protected SessionManager sessionManager;

    /**
     * 初始化参数名称
     */
    public static final String SERVERS = "servers";

    public static final String TIMEOUT = "timeout";

    public static final String POOLSIZE = "poolsize";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        conf = new Configuration();

        String servers = filterConfig.getInitParameter(SERVERS);

        if (StringUtils.isNotBlank(servers)) {
            conf.setServers(servers);
        }

        String timeout = filterConfig.getInitParameter(TIMEOUT);

        if (StringUtils.isNotBlank(timeout)) {
            try {
                conf.setTimeOut(Long.valueOf(timeout));
            } catch (NumberFormatException ex) {
                System.out.println("timeout parse error[" + timeout + "].");
            }
        }

        String poolSize = filterConfig.getInitParameter(POOLSIZE);

        if (StringUtils.isNotBlank(poolSize)) {
            try {
                conf.setPoolSize(Integer.valueOf(poolSize));
            } catch (NumberFormatException ex) {
                System.out.println("poolsize parse error[" + poolSize + "].");
            }
        }

        //初始化ZooKeeper配置参数
       // ZooKeeperHelper.initialize(conf);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        if (sessionManager != null) {
            try {
                sessionManager.stop();
            } catch (Exception e) {
                System.out.println("end");
            }
        }

        //销毁ZooKeeper
        //ZooKeeperHelper.destroy();
    }
}
