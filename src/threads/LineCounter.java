package threads;

import model.SourceFile;
import shared.IBoundedBuffer;

public class LineCounter extends Thread {

    private SourceFile sourceFile;
    private IBoundedBuffer buffer;

    public LineCounter( IBoundedBuffer buffer){
        this.buffer = buffer;
    }

    public synchronized void run(){
        while (!buffer.isEmpty()){
            try {
                SourceFile sf = (SourceFile) buffer.get();
                System.out.println(this.getName() + " " + sf.getPath());
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(this.getName() + "Ended buffer");
    }


}
