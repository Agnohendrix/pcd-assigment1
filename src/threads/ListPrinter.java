package threads;

import model.SourceFile;
import shared.BoundedBuffer1;
import shared.FileList;
import java.util.Comparator;
import gui.GUI;

public class ListPrinter extends Thread {
    private final BoundedBuffer1 buffer;

    private final FileList fl;

    private final GUI gui;

    public ListPrinter(BoundedBuffer1 buffer, FileList fl, GUI gui){
        this.buffer = buffer;
        this.fl = fl;
        this.gui = gui;
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
                if(list.length > 0)
                    //System.out.println("Longest: " + list[list.length-1] + " " + list[0]);
                if(list.length > 0)
                    gui.updateLongestFilesValue(list);
        }
    }
}
