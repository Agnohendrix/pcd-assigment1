public class Searcher extends Thread {
    private int fileCount;

    private String path;
    public Searcher(String path){
        this.path = path;
    }

    public void run(){
            System.out.println("Thread spawned");
    }
}
