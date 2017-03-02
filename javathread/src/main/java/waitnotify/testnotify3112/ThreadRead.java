package waitnotify.testnotify3112;

import java.io.PipedInputStream;

/**
 * Created by root on 17-3-2.
 */
public class ThreadRead extends Thread {
    private ReadData readData;
    private PipedInputStream inputStream;

    public ThreadRead(ReadData readData, PipedInputStream inputStream) {
        this.readData = readData;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        readData.readMethod(inputStream);
    }
}
