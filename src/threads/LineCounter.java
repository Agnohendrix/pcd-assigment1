package threads;

import model.SourceFile;
import shared.IBoundedBuffer;

import java.util.concurrent.CountDownLatch;

public class LineCounter extends Thread {

    private IBoundedBuffer buffer;
    private CountDownLatch latch;
    public LineCounter(IBoundedBuffer buffer, CountDownLatch latch){
        this.buffer = buffer;
        this.latch = latch;
    }

    public synchronized void run(){
        while (!buffer.isEmpty()){
            try {
                SourceFile sf = (SourceFile) buffer.get();
                System.out.println(this.getName() + " " + sf.getPath() + " " + sf.getLength());
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        latch.countDown();
        System.out.println(this.getName() + "Ended buffer");

    }


}
