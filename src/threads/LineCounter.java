package threads;

import model.SourceFile;
import shared.Counter;
import shared.IBoundedBuffer;
import shared.SourceFileList;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LineCounter extends Thread {

    private IBoundedBuffer buffer;
    private CountDownLatch latch;

    private Counter counter;
    private List sfl;
    public LineCounter(IBoundedBuffer buffer, CountDownLatch latch, List sfl, Counter counter){
        this.buffer = buffer;
        this.latch = latch;
        this.sfl = sfl;
        this.counter = counter;
    }

    public synchronized void run(){
        while (!buffer.isEmpty()){
            try {
                SourceFile sf = (SourceFile) buffer.get();
                System.out.println(this.getName() + " " + sf.getPath() + " " + sf.getLength());

                sfl.add(sf);
                counter.inc();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        latch.countDown();
        System.out.println(this.getName() + "Ended buffer");
    }

    private int countLines(){
        return 0;
    }

}
