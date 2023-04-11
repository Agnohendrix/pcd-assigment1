package threads;

import model.SourceFile;
import shared.BoundedBuffer1;
import shared.Counter;

import java.util.List;

public class ListPrinter extends Thread {
    private List sfl;
    private BoundedBuffer1 buffer;

    private Counter counter;

    public ListPrinter(List sfl, BoundedBuffer1 buffer, Counter counter){
        this.sfl = sfl;
        this.buffer = buffer;
        this.counter = counter;
    }
    public synchronized void run(){
        while(!buffer.isEmpty()){
            if(sfl.size() > 0){
                try {

                    SourceFile lastFile = (SourceFile) sfl.get(sfl.size() - 1);
                    System.out.println("sfl thread: " + lastFile.getPath() + " counter: " + counter.getValue());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
