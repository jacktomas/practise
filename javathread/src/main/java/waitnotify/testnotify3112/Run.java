package waitnotify.testnotify3112;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by root on 17-3-2.
 */
public class Run {
    public static void main(String[] args) {
        WriteData writeData = new WriteData();
        ReadData readData = new ReadData();

        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();

        try {

//            outputStream.connect(inputStream);
            inputStream.connect(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ThreadRead threadRead = new ThreadRead(readData, inputStream);
        threadRead.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ThreadWrite threadWrite = new ThreadWrite(writeData, outputStream);

        threadWrite.start();

    }
}
