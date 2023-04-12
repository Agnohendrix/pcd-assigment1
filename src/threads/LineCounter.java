package threads;

import model.SourceFile;
import shared.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LineCounter extends Thread {

    private IBoundedBuffer buffer;
    private CountDownLatch latch;

    private Counter counter;
    private SubdividedCounter scount;
    private List sfl;

    private FileList fl;
    public LineCounter(IBoundedBuffer buffer, CountDownLatch latch, List sfl, Counter counter, SubdividedCounter scount, FileList fl){
        this.buffer = buffer;
        this.latch = latch;
        this.sfl = sfl;
        this.counter = counter;
        this.scount = scount;
        this.fl = fl;
    }

    public synchronized void run(){
        while (!buffer.isEmpty()){
            try {
                SourceFile sf = (SourceFile) buffer.get();
                sf.setLength(countLinesNew(sf.getPath()));
                System.out.println(this.getName() + " " + sf.getPath() + " " + sf.getLength());
                sfl.add(sf);
                counter.inc();
                scount.inc(sf.getLength());
                fl.put(sf);

            } catch (InterruptedException | IOException ex){
                ex.printStackTrace();
            }
        }
        latch.countDown();
        System.out.println(this.getName() + "Ended buffer");
    }

    private static int countLinesNew(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }
            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }
            // count remaining characters
            while (readChars != -1) {
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }
            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }

}
