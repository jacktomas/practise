package waitnotify.testnotify3112;

import java.io.IOException;
import java.io.PipedInputStream;

/**
 * Created by root on 17-3-2.
 */
public class ReadData {
    public void readMethod(PipedInputStream inputStream) {
        System.out.println("read :");
        byte[] byteArray = new byte[20];
        try {
            int readLength = inputStream.read(byteArray);
            while (readLength != -1) {
                String newData = new String(byteArray, 0, readLength);
                System.out.println(newData);
                readLength = inputStream.read(byteArray);
            }
            System.out.println();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
