package threads;

import model.SourceFile;
import shared.BoundedBuffer1;
import shared.Counter;
import shared.FileList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListPrinter extends Thread {
    private List sfl;
    private BoundedBuffer1 buffer;

    private FileList fl;
    private Counter counter;

    public ListPrinter(List sfl, BoundedBuffer1 buffer, Counter counter, FileList fl){
        this.sfl = sfl;
        this.buffer = buffer;
        this.counter = counter;
        this.fl = fl;
    }
    public synchronized void run(){
        while(!buffer.isEmpty()){
            String[] list = fl.get().stream()
                    .sorted(Comparator.comparingInt(SourceFile::getLength))
                    .map(f -> {
                        try {
                            return f.getPath() + " -> " + f.getLength();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(String[]::new);
            System.out.println("Longest n files: " + Arrays.stream(list).count());
           /* if(sfl.size() > 0){
                try {

                    SourceFile lastFile = (SourceFile) sfl.get(sfl.size() - 1);
                    System.out.println("sfl thread: " + lastFile.getPath() + " counter: " + counter.getValue());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }*/
        }
    }

}
