package waitnotify.testnotify3112;

import java.io.PipedOutputStream;

/**
 * Created by root on 17-3-2.
 */
public class ThreadWrite extends Thread {
    private WriteData writeData;

    private PipedOutputStream outputStream;

    public ThreadWrite(WriteData writeData, PipedOutputStream outputStream) {
        this.writeData = writeData;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        writeData.writeMethod(outputStream);
    }
}
