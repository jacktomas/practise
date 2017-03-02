package waitnotify.testnotify34;

import java.util.Date;

/**
 * Created by root on 17-3-2.
 */
public class InheritablethreadLocalExt extends InheritableThreadLocal {

    protected Object initialValue() {
        return new Date().getTime();
    }
}
