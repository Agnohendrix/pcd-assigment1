package shared;

public interface IBoundedBuffer<Item> {

    void put(Item item) throws InterruptedException;

    Item get() throws InterruptedException;

    boolean isEmpty();

    int size();
}
