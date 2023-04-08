import model.SourceFile;
import shared.SourceFileList;
import threads.Searcher;

import java.io.File;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello world!");
        System.out.println(Runtime.getRuntime().availableProcessors() + " cores available");

        SourceFileList<SourceFile> sfl = new SourceFileList<>();

        Cron cron = new Cron();
        cron.start();
        Searcher s = new Searcher(new File("./"), sfl);

        s.start();

        s.join();
        cron.stop();
        System.out.println("Completed in "+cron.getTime()+"ms.");
        System.out.println("List contains " + sfl.getItemCount() + " items");
    }
}