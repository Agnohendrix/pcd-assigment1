import model.SourceFile;
import shared.*;
import threads.LineCounter;
import threads.ListPrinter;
import threads.Searcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if(args.length < 4){
            System.out.println("Missing args");
        }
        System.out.println("Running with parameters "+ args[0] + " " + args[1] + " " + args[2] + " " + args[3]);
        File d = new File(args[0]);
        if(!d.isDirectory()){
            System.out.println(args[0] + " is not a directory");
            return;
        }
        int nFiles = Integer.parseInt(args[1]);
        int nIntervals = Integer.parseInt(args[2]);
        int lMax = Integer.parseInt(args[3]);


        System.out.println("Hello world!");
        System.out.println(Runtime.getRuntime().availableProcessors() + " cores available");

        IBoundedBuffer<SourceFile> buffer = new BoundedBuffer1<>();

        List<SourceFile> sfl = new ArrayList<>();

        Cron cron = new Cron();
        cron.start();
        Searcher s = new Searcher(new File("./"), buffer);

        s.start();

        s.join();


        //Once all file found
        //Counter gets updated whenever a Thread elaborates a new file and continuously prints
        Counter counter = new Counter();
        SubdividedCounter scount = new SubdividedCounter(nIntervals, lMax);
        new CounterPrinter(counter, (BoundedBuffer1) buffer, scount).start();
        int nThreads = 4;
        CountDownLatch latch = new CountDownLatch(nThreads);
        for(int i = 0; i < nThreads; i++)
            new LineCounter(buffer, latch, sfl, counter, scount).start();

        new ListPrinter(sfl, (BoundedBuffer1) buffer, counter).start();
        latch.await();

        cron.stop();
        System.out.println("Completed in "+cron.getTime()+"ms.");
        System.out.println("List contains " + sfl.size() + " items" + counter.getValue());
        int[] scounter = scount.getAllCounters();
        for(int i=0; i< scounter.length; i++){
            System.out.println("Counters " + scounter[i]);
        }

    }
}