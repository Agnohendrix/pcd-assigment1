package shared;

public class CounterPrinter extends Thread {
    private Counter counter;

    private SubdividedCounter scount;
    private BoundedBuffer1 buffer;
    public CounterPrinter(Counter counter, BoundedBuffer1 buffer, SubdividedCounter scount){
        this.counter = counter;
        this.buffer = buffer;
        this.scount = scount;
    }
    public void run(){
        while(!buffer.isEmpty()){
            synchronized (counter){
                //System.out.println("CounterThread value: " + counter.getValue());
            }
            synchronized (scount){
                //Updates correctly, need to fix intervals
                int[] scounters = scount.getAllCounters();
                for (int i = 0; i < scounters.length; i++){
                    //System.out.println("CounterThread array["+i+"] value: " + scounters[i]);
                }

            }
        }

    }
}
