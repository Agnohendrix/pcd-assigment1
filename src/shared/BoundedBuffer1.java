package shared;

import java.util.LinkedList;

public class BoundedBuffer1<Item> implements IBoundedBuffer<Item> {

    private LinkedList<Item> buffer;

    public BoundedBuffer1() {
        buffer = new LinkedList<>();
    }

    public synchronized void put(Item item) throws InterruptedException {

        buffer.addLast(item);
    }

    public synchronized Item get() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        Item item = buffer.removeFirst();
        notifyAll();
        return item;
    }

    public boolean isEmpty() {
        return buffer.size() == 0;
    }

    public int size(){return buffer.size();}
}
