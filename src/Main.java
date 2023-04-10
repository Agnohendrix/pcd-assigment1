import model.SourceFile;
import shared.BoundedBuffer1;
import shared.IBoundedBuffer;
import shared.SourceFileList;
import threads.LineCounter;
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


        //Once all file found
        IBoundedBuffer<SourceFile> buffer = new BoundedBuffer1<>(sfl.getItemCount());

        String sources[] = sfl.getItems();
        System.out.println(sources[sfl.getItemCount() - 1]);
        for(int i = sfl.getItemCount() - 1; i >= 0; i--){
            System.out.println(sources[i]);
            buffer.put(new SourceFile(sources[i], 0));

        }

        int nThreads = 4;
        for(int i = 0; i < nThreads; i++)
            new LineCounter(buffer).start();

        cron.stop();
        System.out.println("Completed in "+cron.getTime()+"ms.");
        System.out.println("List contains " + sfl.getItemCount() + " items");

    }
}