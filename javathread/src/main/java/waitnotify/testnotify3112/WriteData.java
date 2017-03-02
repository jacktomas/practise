package waitnotify.testnotify3112;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * Created by root on 17-3-2.
 */
public class WriteData {
    public void writeMethod(PipedOutputStream out) {
        System.out.println("write :");
        for (int i = 0; i < 300; i++) {
            String outData = "" + (i + 1);
            try {
                out.write(outData.getBytes());
                //System.out.println(outData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
