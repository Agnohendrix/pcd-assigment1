package shared;

import gui.GUI;

public class CounterPrinter extends Thread {


    private SubdividedCounter scount;
    private BoundedBuffer1 buffer;
    private GUI gui;
    public CounterPrinter(BoundedBuffer1 buffer, SubdividedCounter scount, GUI gui){
        this.buffer = buffer;
        this.scount = scount;
        this.gui = gui;
    }
    public void run(){
        while(!buffer.isEmpty()){
            synchronized (scount){
                //Updates correctly, need to fix intervals
                int[] scounters = scount.getAllCounters();
                gui.updateSubdivideCountValue(scounters);
            }
        }
    }
}
