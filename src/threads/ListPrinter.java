package threads;

import model.SourceFile;
import shared.BoundedBuffer1;
import shared.Counter;
import shared.FileList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import gui.GUI;

public class ListPrinter extends Thread {
    private List sfl;
    private BoundedBuffer1 buffer;

    private FileList fl;
    private Counter counter;

    private GUI gui;

    public ListPrinter(List sfl, BoundedBuffer1 buffer, Counter counter, FileList fl, GUI gui){
        this.sfl = sfl;
        this.buffer = buffer;
        this.counter = counter;
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
                    System.out.println("Longest: " + list[list.length-1] + " " + list[0]);
                if(list.length > 0)
                    gui.updateLongestFilesValue(list);

        }
    }

}
