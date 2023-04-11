package shared;

public class CounterPrinter extends Thread {
    private Counter counter;
    private BoundedBuffer1 buffer;
    public CounterPrinter(Counter counter, BoundedBuffer1 buffer){
        this.counter = counter;
        this.buffer = buffer;
    }
    public void run(){
        while(!buffer.isEmpty()){
            synchronized (counter){
                System.out.println("CounterThread value: " + counter.getValue());
            }
        }

    }
}
