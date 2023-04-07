import threads.Searcher;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        System.out.println(Runtime.getRuntime().availableProcessors() + " cores available");

        Cron cron = new Cron();

        cron.start();
        new Searcher("").start();
        cron.stop();
        System.out.println("Completed in "+cron.getTime()+"ms.");
    }
}