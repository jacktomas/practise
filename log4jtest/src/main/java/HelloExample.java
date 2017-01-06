import org.apache.log4j.Logger;

/**
 * Created by root on 17-1-6.
 */
public class HelloExample {
    final static Logger logger = Logger.getLogger(HelloExample.class);

    public static void main(String[] args) {

        HelloExample obj = new HelloExample();
        obj.runMe("mkyong");
        obj.test(0);

    }

    private void runMe(String parameter){

        if(logger.isDebugEnabled()){
            logger.debug("This is debug : " + "debug level is enabled");
        }

        if(logger.isInfoEnabled()){
            logger.info("This is info : " + "debug level is info");
        }

        logger.warn("This is warn : " + parameter);
        logger.error("This is error : " + parameter);
        logger.fatal("This is fatal : " + parameter);

    }

    private void test(int i){
        if(0==i){

            System.out.println("your logic");
            logger.warn("这是不对的参数,值为:"+i);
        }
    }

}
